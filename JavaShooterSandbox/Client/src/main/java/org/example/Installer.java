package org.example;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall
{
    private static Game game;
    @Override
    public void restored()
    {
        game = new Game();
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "GAME";
        cfg.width = 1000;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;
        new LwjglApplication(game, cfg);
    }
}