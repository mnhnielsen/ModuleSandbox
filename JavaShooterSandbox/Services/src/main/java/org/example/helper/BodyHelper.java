package org.example.helper;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper
{
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, float density, World world, ContactType contactType)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic == false ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / Const.PPM, y / Const.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Const.PPM, height / 2 / Const.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(contactType);

        shape.dispose();
        return body;
    }
    public static void destroyBody(Body body, World world){
        world.destroyBody(body);
    }
}
