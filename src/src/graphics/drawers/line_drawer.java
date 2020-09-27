package src.graphics.drawers;

import src.graphics.graphics_utils;
import src.utils.coordinate_2d;

import java.awt.*;

public interface line_drawer extends pixel_drawer
{
    default void draw_line_DDA(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom)
    {
        int delta_x = end.get_x() - begin.get_x();
        int delta_y = end.get_y() - begin.get_y();

        int steps = Math.max(Math.abs(delta_x), Math.abs(delta_y));

        final double x_increment = delta_x / (double)steps;
        final double y_increment = delta_y / (double)steps;

        double x = begin.get_x();
        double y = begin.get_y();

        for (int it = 0; it < steps; ++it)
        {
            draw_pixel(graphics, new coordinate_2d(x, y), color, zoom);
            x += x_increment;
            y += y_increment;
        }
        draw_pixel(graphics, new coordinate_2d(x, y), color, zoom);
    }

    default void draw_line_BRESENHAMS(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom_multiplayer)
    {
        int x_0 = begin.get_x(), y_0 = begin.get_y(), x_1 = end.get_x(), y_1 = end.get_y();

        final int delta_x = Math.abs(x_1 - x_0), x_increment = x_0<x_1 ? 1 : -1;
        final int delta_y = Math.abs(y_1 - y_0), y_increment = y_0<y_1 ? 1 : -1;

        int error_0 = delta_x-delta_y;

        while (x_0 != x_1 || y_0 != y_1)
        {
            draw_pixel(graphics, new coordinate_2d(x_0,y_0), color, zoom_multiplayer);

            final int error_1 = error_0 * 2;

            if (error_1 >= -delta_y)
            {
                error_0 -= delta_y;
                x_0 += x_increment;
            }

            if (error_1 <= delta_x)
            {
                error_0 += delta_x;
                y_0 += y_increment;
            }
        }

        draw_pixel(graphics, new coordinate_2d(x_1,y_1), color, zoom_multiplayer);
    }

    default void draw_line_WU(Graphics2D graphics, coordinate_2d begin, coordinate_2d end, Color color, int zoom_multiplayer)
    {
        int x1 = begin.get_x();
        int x2 = end.get_x();
        int y1 = begin.get_y();
        int y2 = end.get_y();

        final int delta_x = x2 - x1;
        final int delta_y = y2 - y1;
        int x_direction = Math.round(Math.signum(delta_x));
        int y_direction = Math.round(Math.signum(delta_y));

        if (Math.abs(delta_x) < Math.abs(delta_y))
        {
            double gradient = (double)delta_x / delta_y;
            if ((int)Math.signum(x_direction * gradient) != 1)
                gradient *= -1;

            for (double x = x1, y = y1; y - y_direction != y2; y += y_direction, x += gradient)
            {
                double doublePart = Math.abs(x - (int)x);
                draw_pixel(graphics, new coordinate_2d((int)x, y), graphics_utils.get_color_with_alpha(color, 1 - doublePart) , zoom_multiplayer);
                draw_pixel(graphics, new coordinate_2d((int)x + 1, y), graphics_utils.get_color_with_alpha(color, doublePart), zoom_multiplayer);
            }
        }
        else
        {
            double gradient = (double) delta_y / delta_x;
            if ((int) Math.signum(y_direction * gradient) != 1)
                gradient *= -1;

            for (double x = x1, y = y1; x - x_direction != x2; x += x_direction, y += gradient)
            {
                double doublePart = Math.abs(y - (int)y);
                draw_pixel(graphics, new coordinate_2d(x, (int)y), graphics_utils.get_color_with_alpha(color, 1 - doublePart), zoom_multiplayer);
                draw_pixel(graphics, new coordinate_2d(x, (int)y + 1), graphics_utils.get_color_with_alpha(color, doublePart), zoom_multiplayer);
            }
        }
    }
}
