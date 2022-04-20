package org.example.helper;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class AssetFileResolver implements FileHandleResolver
{
    @Override
    public FileHandle resolve(String fileName) {

        return new JarFileResolver(fileName);
    }
}
