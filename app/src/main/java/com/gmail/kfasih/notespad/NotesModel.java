package com.gmail.kfasih.notespad;

import java.util.Objects;

public class NotesModel {
    private int id = -1;
    private String title;
    private String description;
    private String date;
    private String imagePath;
    private int numOfCharacter;
    private int backgroundResourceId;

    // Constants for DB of Notes

    public static final String TABLE_NAME = "NotesTable";
    public static final String COL_ID = "Id";
    public static final String COL_Title = "Title";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_DATE = "Date";
    public static final String COL_IMAGE_PATH = "ImagePath";
    public static final String COL_NUM_OF_CHARACTER = "NumOfCharacter";
    public static final String COL_BACKGROUND_RESOURCE_ID = "BackgroundResourceId";

    // Constant For DB Command
    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER)",NotesModel.TABLE_NAME, NotesModel.COL_ID, NotesModel.COL_Title, NotesModel.COL_DESCRIPTION, NotesModel.COL_DATE, NotesModel.COL_IMAGE_PATH, NotesModel.COL_NUM_OF_CHARACTER, NotesModel.COL_BACKGROUND_RESOURCE_ID);
    public static final String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", NotesModel.TABLE_NAME);
    public static final String SELECT_ALL = String.format("SELECT * FROM %s", NotesModel.TABLE_NAME);
    public static final String SELECT_BY_ID = String.format("SELECT * FROM %s WHERE %s = ?", NotesModel.TABLE_NAME, NotesModel.COL_ID);
    

    public NotesModel() {
    }

    public NotesModel(int id, String title, String description, String date, String imagePath, int backgroundResourceId, int numOfCharacter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagePath = imagePath;
        this.backgroundResourceId = backgroundResourceId;
        this.numOfCharacter = numOfCharacter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getBackgroundResourceId() {
        return backgroundResourceId;
    }

    public void setBackgroundResourceId(int backgroundResourceId) {
        this.backgroundResourceId = backgroundResourceId;
    }

    public int getNumOfCharacter() {
        return numOfCharacter;
    }

    public void setNumOfCharacter(int numOfCharacter) {
        this.numOfCharacter = numOfCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesModel that = (NotesModel) o;
        return id == that.id && numOfCharacter == that.numOfCharacter && backgroundResourceId == that.backgroundResourceId && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, date, imagePath, numOfCharacter, backgroundResourceId);
    }

    @Override
    public String toString() {
        return "NotesModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", numOfCharacter=" + numOfCharacter +
                ", backgroundResourceId=" + backgroundResourceId +
                '}';
    }

}
