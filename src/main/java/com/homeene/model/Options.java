package com.homeene.model;

public class Options {
    private Integer id;

    private Integer questionId;

    private String choices;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    
	public String getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}