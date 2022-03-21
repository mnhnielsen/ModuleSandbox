package org.example;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.example.helper.Boot;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall
{
    @Override
    public void restored()
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Pong";
        cfg.width = 1200;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;
        new LwjglApplication(new Boot(), cfg);
    }
}