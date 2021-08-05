package com.MovieRecSys.online.model;

import java.util.ArrayList;

public class Embedding {
    ArrayList<Float> embVector;

    public Embedding() {
        this.embVector = new ArrayList<>();
    }

    public Embedding(ArrayList<Float> embVector) {
        this.embVector = embVector;
    }

    public void addDim(Float element) {
        this.embVector.add(element);
    }

    public ArrayList<Float> getEmbVector() {
        return embVector;
    }

    public void setEmbVector(ArrayList<Float> embVector) {
        this.embVector = embVector;
    }

    //calculate cosine similarity between two embeddings
    public double calculateSimilarity(Embedding otherEmb) {
        if (null == embVector || null == otherEmb || null == otherEmb.getEmbVector() || embVector.size() != otherEmb.getEmbVector().size()) {
            return -1;
        }
        double dotProduct = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;
        for (int i = 0; i < embVector.size(); i++) {
            dotProduct += embVector.get(i) * otherEmb.embVector.get(i);
            denominator1 += embVector.get(i) * embVector.get(i);
            denominator2 += otherEmb.embVector.get(i) * otherEmb.embVector.get(i);
        }
        return dotProduct / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
    }
}
