package com.henry.tree;

public enum RedBlack
{
    RED("red"),BLACK("black");
    
    private final String value;
    private RedBlack(String value)
    {
        this.value=value;
    }
    
    public String getValue(){
        return this.value;
    }
}
