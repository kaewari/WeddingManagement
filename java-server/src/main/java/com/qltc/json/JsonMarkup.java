package com.qltc.json;

public interface JsonMarkup {
    
    public static interface Identity {};
    
    public static interface CoreData extends Identity {};
    
    public static interface FullData extends CoreData {};
    
    public static interface FetchedData extends CoreData {};
    
    public static interface HiddenData extends FullData, FetchedData {};
}
