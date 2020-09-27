package src.graphics.drawers;

import src.utils.coordinate_2d;

import java.awt.*;

public class line_graphics implements line_drawer
{
    public line_graphics() {}

    public void draw_line_DDA(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom_multiplayer)
    {
        line_drawer.super.draw_line_DDA(graphics, begin, end, color, zoom_multiplayer);
    }

    public void draw_line_BRESENHAMS(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom_multiplayer)
    {
        line_drawer.super.draw_line_BRESENHAMS(graphics, begin, end, color, zoom_multiplayer);
    }

    public void draw_line_WU(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom_multiplayer)
    {
        line_drawer.super.draw_line_WU(graphics, begin, end, color, zoom_multiplayer);
    }
}
