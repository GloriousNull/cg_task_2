package src.graphics.drawers;

import src.graphics.fillers.arc_filler;
import src.utils.coordinate_2d;

import java.awt.*;

public class arc_graphics implements arc_drawer, arc_filler
{
    private final boolean is_alpha;

    public arc_graphics(boolean is_alpha)
    {
        this.is_alpha = is_alpha;
    }

    public void fill_arc(Graphics2D graphics, coordinate_2d center, int width, int height, int begin_angle, int end_angle, Color color, int zoom_multiplayer)
    {
        arc_filler.super.fill_arc(graphics, center, width, height, begin_angle, end_angle, color, zoom_multiplayer);
    }

    public void draw_arc(Graphics2D graphics, coordinate_2d center, int width, int height, int begin_angle, int end_angle, Color color, int zoom_multiplayer)
    {
        arc_drawer.super.draw_arc(graphics, center, width, height, begin_angle, end_angle, color, zoom_multiplayer, is_alpha);
    }
}
