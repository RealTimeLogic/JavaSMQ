package eu.hansolo.steelseries.tools;

/*
 ** Copyright 2005 Huxtable.com. All rights reserved.
 */
public class BrushedMetalFilter implements java.awt.image.BufferedImageOp
{
    private int radius;
    private float amount = 0.1f;
    private int color = 0xff888888;
    private float shine = 0;
    private boolean monochrome = false;
    private java.util.Random randomNumbers;

    public BrushedMetalFilter()
    {
    }

    public BrushedMetalFilter(final int COLOR, final int RADIUS, final float AMOUNT, final boolean MONOCHROME, final float SHINE)
    {
        this.color = COLOR;
        this.radius = RADIUS;
        this.amount = AMOUNT;
        this.monochrome = MONOCHROME;
        this.shine = SHINE;
    }

    @Override
    public java.awt.image.BufferedImage filter(final java.awt.image.BufferedImage SOURCE, java.awt.image.BufferedImage destination)
    {
        final int WIDTH = SOURCE.getWidth();
        final int HEIGHT = SOURCE.getHeight();

        if (destination == null)
        {
            destination = createCompatibleDestImage(SOURCE, null);
        }

        final int[] IN_PIXELS = new int[WIDTH];
        final int[] OUT_PIXELS = new int[WIDTH];

        randomNumbers = new java.util.Random(0);
        final int ALPHA = color & 0xff000000;
        final int RED = (color >> 16) & 0xff;
        final int GREEN = (color >> 8) & 0xff;
        final int BLUE = color & 0xff;
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                int tr = RED;
                int tg = GREEN;
                int tb = BLUE;
                if (shine != 0)
                {
                    int f = (int) (255 * shine * Math.sin((double) x / WIDTH * Math.PI));
                    tr += f;
                    tg += f;
                    tb += f;
                }
                if (monochrome)
                {
                    int n = (int) (255 * (2 * randomNumbers.nextFloat() - 1) * amount);
                    IN_PIXELS[x] = ALPHA | (clamp(tr + n) << 16) | (clamp(tg + n) << 8) | clamp(tb + n);
                }
                else
                {
                    IN_PIXELS[x] = ALPHA | (random(tr) << 16) | (random(tg) << 8) | random(tb);
                }
            }

            if (radius != 0)
            {
                blur(IN_PIXELS, OUT_PIXELS, WIDTH, radius);
                setRGB(destination, 0, y, WIDTH, 1, OUT_PIXELS);
            }
            else
            {
                setRGB(destination, 0, y, WIDTH, 1, IN_PIXELS);
            }
        }
        return destination;
    }

    private int random(int x)
    {
        x += (int) (255 * (2 * randomNumbers.nextFloat() - 1) * amount);
        if (x < 0)
        {
            x = 0;
        }
        else if (x > 0xff)
        {
            x = 0xff;
        }
        return x;
    }

    private static int clamp(final int C)
    {
        int ret = C;
        if (C < 0)
        {
            ret = 0;
        }
        if (C > 255)
        {
            ret = 255;
        }
        return ret;
    }

    /**
     * Return a mod b. This differs from the % operator with respect to negative numbers.
     * @param a the dividend
     * @param B the divisor
     * @return a mod b
     */
    private static int mod(int a, final int B)
    {
        final int N = a / B;

        a -= N * B;
        if (a < 0)
        {
            return a + B;
        }
        return a;
    }

    public void blur(final int[] IN, final int[] OUT, final int WIDTH, final int RADIUS)
    {
        final int WIDTH_MINUS_1 = WIDTH - 1;
        final int R2 = 2 * RADIUS + 1;
        int tr = 0, tg = 0, tb = 0;

        for (int i = -RADIUS; i <= RADIUS; i++)
        {
            int rgb = IN[mod(i, WIDTH)];
            tr += (rgb >> 16) & 0xff;
            tg += (rgb >> 8) & 0xff;
            tb += rgb & 0xff;
        }

        for (int x = 0; x < WIDTH; x++)
        {
            OUT[x] = 0xff000000 | ((tr / R2) << 16) | ((tg / R2) << 8) | (tb / R2);

            int i1 = x + RADIUS + 1;
            if (i1 > WIDTH_MINUS_1)
            {
                i1 = mod(i1, WIDTH);
            }
            int i2 = x - RADIUS;
            if (i2 < 0)
            {
                i2 = mod(i2, WIDTH);
            }
            int rgb1 = IN[i1];
            int rgb2 = IN[i2];

            tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
            tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
            tb += (rgb1 & 0xff) - (rgb2 & 0xff);
        }
    }

    public void setRadius(final int RADIUS)
    {
        this.radius = RADIUS;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setAmount(final float AMOUNT)
    {
        this.amount = AMOUNT;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setColor(final int COLOR)
    {
        this.color = COLOR;
    }

    public int getColor()
    {
        return color;
    }

    public void setMonochrome(final boolean MONOCHROME)
    {
        this.monochrome = MONOCHROME;
    }

    public boolean getMonochrome()
    {
        return monochrome;
    }

    public void setShine(final float SHINE)
    {
        this.shine = SHINE;
    }

    public float getShine()
    {
        return shine;
    }

    @Override
    public java.awt.image.BufferedImage createCompatibleDestImage(final java.awt.image.BufferedImage SOURCE, java.awt.image.ColorModel dstCM)
    {
        if (dstCM == null)
        {
            dstCM = SOURCE.getColorModel();
        }
        return new java.awt.image.BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(SOURCE.getWidth(), SOURCE.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }

    @Override
    public java.awt.geom.Rectangle2D getBounds2D(final java.awt.image.BufferedImage SOURCE)
    {
        return new java.awt.Rectangle(0, 0, SOURCE.getWidth(), SOURCE.getHeight());
    }

    @Override
    public java.awt.geom.Point2D getPoint2D(final java.awt.geom.Point2D SOURCE_POINT, java.awt.geom.Point2D dstPt)
    {
        if (dstPt == null)
        {
            dstPt = new java.awt.geom.Point2D.Double();
        }
        dstPt.setLocation(SOURCE_POINT.getX(), SOURCE_POINT.getY());
        return dstPt;
    }

    @Override
    public java.awt.RenderingHints getRenderingHints()
    {
        return null;
    }

    /**
     * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
     * penalty of BufferedImage.setRGB unmanaging the image.
     */
    public void setRGB(final java.awt.image.BufferedImage IMAGE, final int X, final int Y, final int WIDTH, final int HEIGHT, final int[] PIXELS)
    {
        int type = IMAGE.getType();
        if (type == java.awt.image.BufferedImage.TYPE_INT_ARGB || type == java.awt.image.BufferedImage.TYPE_INT_RGB)
        {
            IMAGE.getRaster().setDataElements(X, Y, WIDTH, HEIGHT, PIXELS);
        }
        else
        {
            IMAGE.setRGB(X, Y, WIDTH, HEIGHT, PIXELS, 0, WIDTH);
        }
    }

    @Override
    public String toString()
    {
        return "Texture/Brushed Metal...";
    }
}
