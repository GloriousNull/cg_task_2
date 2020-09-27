package src.graphics.drawers;

import src.utils.coordinate_2d;

import java.awt.*;

public interface pixel_drawer 
{
    default void draw_pixel(Graphics2D graphics, coordinate_2d point, Color color)
    {
        graphics.setColor(color);
        graphics.fillRect(point.get_x(), point.get_y(), 1, 1);
    }

    default void draw_pixel(Graphics2D graphics, coordinate_2d point, Color color, int zoom_multiplayer)
    {
        graphics.setColor(color);
        graphics.fillRect(point.get_x()*zoom_multiplayer, point.get_y()*zoom_multiplayer, zoom_multiplayer, zoom_multiplayer);
    }
}
