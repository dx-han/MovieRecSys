package com.MovieRecSys.online;

import com.MovieRecSys.online.datamanager.DataManager;
import com.MovieRecSys.online.service.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;


/**
 * server, starter of online recommendation service
 */
public class RecSysServer {
    private static final int DEFAULT_PORT = 6010;

    public static void main(String[] args) throws Exception {
        new RecSysServer().run();

    }

    public void run() throws Exception {
        int port = DEFAULT_PORT;
        try {
            port = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException ignored) {
        }

        //set ip and port number
        InetSocketAddress inetAddress = new InetSocketAddress("0.0.0.0", port);
        Server server = new Server(inetAddress);

        //get index.html path
        URL webRootLocation = this.getClass().getResource("/webroot/index.html");
        System.out.printf("Web root URL: %s%n", webRootLocation.getPath());
        if (webRootLocation == null) {
            throw new IllegalStateException("Unable to determine URL location.");
        }

        //set index.html as the root page
        String path = webRootLocation.toURI().toASCIIString().replaceFirst("/index.html$","/");
        URI webRootUri = new URI(path);
        System.out.printf("Web root URI: %s%n", webRootUri.getPath());

        //load all the data to DataManager
        DataManager.getInstance().loadData("/webroot/sampledata/movies.csv",
                "/webroot/sampledata/links.csv","/webroot/sampledata/ratings.csv",
                "/webroot/modeldata/item2vecEmb.csv", "/webroot/modeldata/userEmb.csv",
                "i2vEmb", "uEmb");

        //create server context
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.setBaseResource(Resource.newResource(webRootUri));
        context.setWelcomeFiles(new String[] {"index.html"});
        context.getMimeTypes().addMimeMapping("txt","text/plain;charset=utf-8");

        //bind services with different servlets
        context.addServlet(DefaultServlet.class,"/");
        context.addServlet(new ServletHolder(new MovieService()), "/getmovie");
        context.addServlet(new ServletHolder(new UserService()), "/getuser");
        context.addServlet(new ServletHolder(new SimilarMovieService()), "/getsimilarmovie");
        context.addServlet(new ServletHolder(new RecommendationService()), "/getrecommendation");
        context.addServlet(new ServletHolder(new RecForYouService()), "/getrecforyou");

        //set url handler
        server.setHandler(context);
        System.out.println("RecSys Server has started.");

        //start Server
        server.start();
        server.join();
    }
}
