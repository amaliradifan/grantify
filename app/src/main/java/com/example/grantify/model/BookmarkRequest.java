package com.example.grantify.model;

public class BookmarkRequest {
    private String programId;

    public BookmarkRequest(String programId) {
        this.programId = programId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
}

