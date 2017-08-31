package com.homeene.model;

public class Award {
    private Integer id;

    private String name;

    private String url;

    private float probability;
    
    private String content;

    private Integer count;
    
    private Integer issue;

    public Award(int i, String string, float f, int j) {
		// TODO Auto-generated constructor stub
    	this.id=i;
    	this.name=string;
    	this.probability=(int) f;
    	this.count=j;
	}

	public Award() {
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    
	public String getContent() {
		return content;
	}

	
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

	
	public Integer getIssue() {
		return issue;
	}

	
	public void setIssue(Integer issue) {
		this.issue = issue;
	}
    
    
}