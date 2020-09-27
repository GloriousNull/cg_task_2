package src.graphics.drawers;

import src.utils.coordinate_2d;

import java.awt.*;

public interface ellipse_drawer extends pixel_drawer
{
    default void draw_ellipse_BRESENHAMS(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer)
    {
        int x = 0, y = height;

        int error = 4 * y * y + width * width * (2 * y - 1) * (2 * y - 1) - 4 * y * y * width * width;

        int height_sqr = height * height;
        int width_sqr = width * width;

        while (width_sqr * (2 * y - 1) > 2 * height_sqr * (x + 1))
        {
            pixel_mirroring(graphics, center, x, y, color, zoom_multiplayer);

            ++x;

            if (error < 0)
                error += 4 * height_sqr * (2 * x + 3);
            else
            {
                error += -8 * width_sqr * (y - 1) + 4 * height_sqr * (2 * x + 3);
                --y;
            }
        }

        error = height_sqr * ((2 * x + 1) * (2 * x + 1)) + 4 * width_sqr * ((y+1) * (y+1)) - 4*width_sqr*height_sqr;

        while (y != -1)
        {
            pixel_mirroring(graphics, center, x, y, color, zoom_multiplayer);

            --y;

            if (error < 0)
                error += 4 * width_sqr * (2 * y + 3);
            else
            {
                error += -8 * height_sqr * (x - 1) + 4 * width_sqr * (2 * y + 3);
                ++x;
            }
        }
    }

    default void draw_ellipse_WUlike(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer, boolean is_alpha)
    {
        final int height_sqr = height * height;
        final int width_sqr = width * width;

        final float max_transparency = 255;
        int quarter = (int)Math.round(width_sqr/Math.sqrt(height_sqr+width_sqr));

        for (int x = 0; x <= quarter; ++x)
        {
            double y = height * Math.sqrt(1 - (double)x*x/width_sqr);
            double error = y - Math.floor(y);

            double transparency = Math.round(error * max_transparency);
            double alpha_0 = is_alpha ? transparency : 255;
            double alpha_1 = is_alpha ? max_transparency - transparency : 255;

            pixel_mirroring(graphics, center, x, (int)Math.floor(y), new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_0)), zoom_multiplayer);
            pixel_mirroring(graphics, center, x, (int)Math.floor(y)-1, new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_1)), zoom_multiplayer);
        }

        quarter = (int)Math.round(height_sqr/Math.sqrt(height_sqr+width_sqr));

        for (int y = 0; y <= quarter; ++y)
        {
            double x = width * Math.sqrt(1 - (double)y*y/height_sqr);
            double error = x - Math.floor(x);

            double transparency = (int)Math.round(error * max_transparency);
            double alpha_0 = is_alpha ? transparency : 255;
            double alpha_1 = is_alpha ? max_transparency - transparency : 255;

            pixel_mirroring(graphics, center, (int)Math.floor(x), y, new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_0)), zoom_multiplayer);
            pixel_mirroring(graphics, center, (int)Math.floor(x)-1, y, new Color(color.getRed(), color.getGreen(),color.getBlue(), (int)Math.round(alpha_1)), zoom_multiplayer);
        }
    }

    private void pixel_mirroring(Graphics2D graphics, coordinate_2d center, int x_offset, int y_offset, Color color, int zoom_multiplayer)
    {
        draw_pixel(graphics, new coordinate_2d(center.get_x() + x_offset, center.get_y() + y_offset), color, zoom_multiplayer);
        draw_pixel(graphics, new coordinate_2d(center.get_x() + x_offset, center.get_y() - y_offset), color, zoom_multiplayer);
        draw_pixel(graphics, new coordinate_2d(center.get_x() - x_offset, center.get_y() + y_offset), color, zoom_multiplayer);
        draw_pixel(graphics, new coordinate_2d(center.get_x() - x_offset, center.get_y() - y_offset), color, zoom_multiplayer);
    }
}
