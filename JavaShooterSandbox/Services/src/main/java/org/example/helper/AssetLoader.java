package org.example.helper;

import com.badlogic.gdx.assets.AssetManager;

public class AssetLoader
{
    public static AssetLoader INSTANCE;
    AssetFileResolver assetFileResolver = new AssetFileResolver();
    AssetManager am = new AssetManager(assetFileResolver);

    public AssetLoader()
    {
        INSTANCE = this;
    }

    public AssetManager getAm()
    {
        return am;
    }
}
