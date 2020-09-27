package src.graphics.drawers;

import src.graphics.fillers.ellipse_filler;
import src.utils.coordinate_2d;

import java.awt.*;

public class ellipse_graphics implements ellipse_drawer, ellipse_filler
{
    private boolean is_alpha = false;

    public ellipse_graphics(boolean is_alpha)
    {
        this.is_alpha = is_alpha;
    }

    public void draw_circle_BRESENHAMS(Graphics2D graphics, coordinate_2d center, int radius, Color color, int zoom_multiplayer)
    {
        ellipse_drawer.super.draw_ellipse_BRESENHAMS(graphics, center, radius, radius, color, zoom_multiplayer);
    }

    public void draw_ellipse_BRESENHAMS(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer)
    {
        ellipse_drawer.super.draw_ellipse_BRESENHAMS(graphics, center, width, height, color, zoom_multiplayer);
    }

    public void draw_circle_WUlike(Graphics2D graphics, coordinate_2d center, int radius, Color color, int zoom_multiplayer)
    {
        ellipse_drawer.super.draw_ellipse_WUlike(graphics, center, radius, radius, color, zoom_multiplayer, is_alpha);
    }

    public void draw_ellipse_WUlike(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer)
    {
        ellipse_drawer.super.draw_ellipse_WUlike(graphics, center, width, height, color, zoom_multiplayer, is_alpha);
    }

    public void fill_ellipse(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer)
    {
        ellipse_filler.super.fill_ellipse(graphics, center, width, height, color, zoom_multiplayer);
    }

    public void fill_circle(Graphics2D graphics, coordinate_2d center, int radius, Color color, int zoom_multiplayer)
    {
        ellipse_filler.super.fill_circle(graphics, center, radius, color, zoom_multiplayer);
    }
}
