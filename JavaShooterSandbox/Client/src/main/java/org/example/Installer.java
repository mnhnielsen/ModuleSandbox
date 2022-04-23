package org.example;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.example.helper.AssetLoader;
import org.example.helper.Boot;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall
{
    @Override
    public void restored()
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Shooter game";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;
        new AssetLoader();
        new LwjglApplication(new Boot(), cfg);
    }
}