package com.sekwah.reskin.client;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class HDImageBufferDownload implements IImageBuffer {

    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    public BufferedImage parseUserSkin(BufferedImage image)
    {
        if (image == null)
        {
            return null;
        }
        else
        {
            this.imageWidth = image.getWidth();
            this.imageHeight = image.getHeight();
            boolean flag = this.imageHeight == this.imageWidth / 2;
            BufferedImage bufferedimage = new BufferedImage(this.imageWidth, flag ? this.imageWidth : this.imageHeight, 2);
            Graphics graphics = bufferedimage.getGraphics();
            graphics.drawImage(image, 0, 0, (ImageObserver)null);

            if (flag)
            {
                graphics.setColor(new Color(0, 0, 0, 0));
                graphics.fillRect(0, this.imageHeight, this.imageWidth, this.imageHeight);
                graphics.drawImage(bufferedimage, (int) (24f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth), (int) (20f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (4f / 64f * this.imageWidth), (int) (16f / 32f * this.imageHeight), (int) (8f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (28f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth), (int) (24f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (8f / 64f * this.imageWidth), (int) (6f / 32f * this.imageHeight), (int) (12f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (20f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (16f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (8f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (int) (12f / 64f * this.imageWidth), (int) (32f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (24f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (20f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (4f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (int) (8f / 64f * this.imageWidth), (int) (32f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (28f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (24f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (0f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (int) (4f / 64f * this.imageWidth), (int) (32f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (32f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (28f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (12f / 64f * this.imageWidth),(int) (20f / 32f * this.imageHeight), (int) (16f / 64f * this.imageWidth), (int) (32f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (40f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth), (int) (36f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (44f / 64f * this.imageWidth),(int) (16f / 32f * this.imageHeight), (int) (48f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (44f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth), (int) (40f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth),(int) (16f / 32f * this.imageHeight), (int) (52f / 64f * this.imageWidth), (int) (20f / 32f * this.imageHeight), (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (36f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (32f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (48f / 64f * this.imageWidth),(int) (20f / 32f * this.imageHeight), (int) (52f / 64f * this.imageWidth), this.imageHeight, (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (40f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (36f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (44f / 64f * this.imageWidth),(int) (20f / 32f * this.imageHeight), (int) (48f / 64f * this.imageWidth), this.imageHeight, (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (44f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (40f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (40f / 64f * this.imageWidth),(int) (20f / 32f * this.imageHeight), (int) (44f / 64f * this.imageWidth), this.imageHeight, (ImageObserver)null);
                graphics.drawImage(bufferedimage, (int) (48f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth), (int) (44f / 64f * this.imageWidth), (int) (64f / 64f * this.imageWidth), (int) (52f / 64f * this.imageWidth),(int) (20f / 32f * this.imageHeight), (int) (56f / 64f * this.imageWidth), this.imageHeight, (ImageObserver)null);
            }

            graphics.dispose();
            this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
            this.setAreaOpaque(0, 0, (int) (this.imageWidth * 0.5), (int) (this.imageWidth * 0.25f));

            if (flag)
            {
                this.setAreaTransparent(this.imageWidth / 2, 0, this.imageWidth, this.imageWidth / 2);
            }

            this.setAreaOpaque(0, (int) (this.imageWidth * 0.25f), this.imageWidth, (int) (this.imageWidth * 0.5));
            this.setAreaOpaque((int) (this.imageWidth * 0.25f), (int) (this.imageWidth * 0.75f), (int) (this.imageWidth * 0.75f), this.imageWidth);
            return bufferedimage;
        }
    }

    public void skinAvailable()
    {
    }

    private void setAreaTransparent(int x, int y, int width, int height)
    {
        for (int i = x; i < width; ++i)
        {
            for (int j = y; j < height; ++j)
            {
                int k = this.imageData[i + j * this.imageWidth];

                if ((k >> 24 & 255) < 128)
                {
                    return;
                }
            }
        }

        for (int l = x; l < width; ++l)
        {
            for (int i1 = y; i1 < height; ++i1)
            {
                this.imageData[l + i1 * this.imageWidth] &= 16777215;
            }
        }
    }

    /**
     * Makes the given area of the image opaque
     */
    private void setAreaOpaque(int x, int y, int width, int height)
    {
        for (int i = x; i < width; ++i)
        {
            for (int j = y; j < height; ++j)
            {
                this.imageData[i + j * this.imageWidth] |= -16777216;
            }
        }
    }

}
