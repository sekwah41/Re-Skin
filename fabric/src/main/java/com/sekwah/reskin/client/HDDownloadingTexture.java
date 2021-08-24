package com.sekwah.reskin.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class HDDownloadingTexture extends SimpleTexture {

    private static final Logger LOGGER = LogManager.getLogger();

    private final File cacheFile;
    private final String imageUrl;

    private int imageWidth;
    private int imageHeight;
    private boolean isTransparent;

    private final boolean legacySkin = true;

    private final Runnable processTask;

    private CompletableFuture<?> future;
    private boolean textureUploaded;

    public HDDownloadingTexture(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, boolean isTransparent, Runnable processTaskIn) {
        super(textureResourceLocation);
        this.cacheFile = cacheFileIn;
        this.imageUrl = imageUrlIn;
        this.isTransparent = isTransparent;
        this.processTask = processTaskIn;
    }

    private void setImage(NativeImage nativeImageIn) {
        if (this.processTask != null) {
            this.processTask.run();
        }

        Minecraft.getInstance().execute(() -> {
            this.textureUploaded = true;
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> {
                    this.upload(nativeImageIn);
                });
            } else {
                this.upload(nativeImageIn);
            }

        });
    }

    private void upload(NativeImage imageIn) {
        TextureUtil.prepareImage(this.getId(), imageIn.getWidth(), imageIn.getHeight());
        imageIn.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        Minecraft.getInstance().execute(() -> {
            if (!this.textureUploaded) {
                try {
                    super.load(manager);
                } catch (IOException ioexception) {
                    LOGGER.warn("Failed to load texture: {}", this.location, ioexception);
                }

                this.textureUploaded = true;
            }

        });
        if (this.future == null) {
            NativeImage nativeimage;
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                LOGGER.debug("Loading http texture from local cache ({})", (Object)this.cacheFile);
                FileInputStream fileinputstream = new FileInputStream(this.cacheFile);
                nativeimage = this.loadTexture(fileinputstream);
            } else {
                nativeimage = null;
            }

            if (nativeimage != null) {
                this.setImage(nativeimage);
            } else {
                this.future = CompletableFuture.runAsync(() -> {
                    HttpURLConnection httpurlconnection = null;
                    LOGGER.debug("Downloading http texture from {} to {}", this.imageUrl, this.cacheFile);

                    try {
                        httpurlconnection = (HttpURLConnection)(new URL(this.imageUrl)).openConnection(Minecraft.getInstance().getProxy());
                        httpurlconnection.setDoInput(true);
                        httpurlconnection.setDoOutput(false);
                        httpurlconnection.connect();
                        if (httpurlconnection.getResponseCode() == 200) {
                            InputStream inputstream;
                            if (this.cacheFile != null) {
                                FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), this.cacheFile);
                                inputstream = new FileInputStream(this.cacheFile);
                            } else {
                                inputstream = httpurlconnection.getInputStream();
                            }

                            NativeImage nativeimage1 = this.loadTexture(inputstream);
                            if (nativeimage1 != null) {
                                this.setImage(nativeimage1);
                            }
                            return;
                        }
                    } catch (Exception exception) {
                        LOGGER.error("Couldn't download http texture", (Throwable)exception);
                        return;
                    } finally {
                        if (httpurlconnection != null) {
                            httpurlconnection.disconnect();
                        }

                    }

                }, Util.backgroundExecutor());
            }
        }
    }

    private NativeImage loadTexture(InputStream inputStreamIn) {
        NativeImage nativeimage = null;

        try {
            nativeimage = NativeImage.read(inputStreamIn);
            if (this.legacySkin) {
                nativeimage = processLegacySkin(nativeimage);
            }
        } catch (IOException ioexception) {
            LOGGER.warn("Error while loading the skin texture", (Throwable)ioexception);
        }

        return nativeimage;
    }

    /**
     * HttpTexture#processLegacySkin(com.mojang.blaze3d.platform.NativeImage)
     * @param nativeImageIn
     * @return
     */
    private NativeImage processLegacySkin(NativeImage nativeImageIn) {
        if(nativeImageIn == null) {
            return null;
        }
        this.imageWidth = nativeImageIn.getWidth();
        this.imageHeight = nativeImageIn.getHeight();
        boolean flag = this.imageHeight == this.imageWidth / 2;
        float scaleFactor = (1f / 64f) * this.imageWidth;
        if (flag) {
            NativeImage nativeimage = new NativeImage(this.imageWidth, this.imageWidth, true);
            nativeimage.copyFrom(nativeImageIn);
            nativeImageIn.close();
            nativeImageIn = nativeimage;
            if(!isTransparent) {
                nativeimage.fillRect(0, (int) (32 * scaleFactor), (int) (64 * scaleFactor), (int) (32 * scaleFactor), 0);
            }
            copyAreaRGBAScale(nativeimage, 4, 16, 16, 32, 4, 4, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 8, 16, 16, 32, 4, 4, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 0, 20, 24, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 4, 20, 16, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 8, 20, 8, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 12, 20, 16, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 44, 16, -8, 32, 4, 4, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 48, 16, -8, 32, 4, 4, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 40, 20, 0, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 44, 20, -8, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 48, 20, -16, 32, 4, 12, true, false, scaleFactor);
            copyAreaRGBAScale(nativeimage, 52, 20, -8, 32, 4, 12, true, false, scaleFactor);
        }


        if(!isTransparent) {
            setAreaOpaque(nativeImageIn, 0, 0, (int) (32 * scaleFactor), (int) (16 * scaleFactor));
        }
        if (flag) {
            setAreaTransparent(nativeImageIn, 32, 0, (int) (64 * scaleFactor), (int) (32 * scaleFactor));
        }

        if(!isTransparent) {
            setAreaOpaque(nativeImageIn, 0, (int) (16 * scaleFactor), (int) (64 * scaleFactor), (int) (32 * scaleFactor));
            setAreaOpaque(nativeImageIn, (int) (16 * scaleFactor), (int) (48 * scaleFactor), (int) (48 * scaleFactor), (int) (64 * scaleFactor));
        }
        return nativeImageIn;
    }

    private static void copyAreaRGBAScale(NativeImage nativeimage, int xFrom, int yFrom, int xToDelta, int yToDelta, int widthIn, int heightIn, boolean mirrorX, boolean mirrorY, float scaleFactor) {
        nativeimage.copyRect((int) (xFrom * scaleFactor),
                (int) (yFrom * scaleFactor),
                (int) (xToDelta * scaleFactor),
                (int) (yToDelta * scaleFactor),
                (int) (widthIn * scaleFactor),
                (int) (heightIn * scaleFactor),
                mirrorX,
                mirrorY);
    }

    private static void setAreaTransparent(NativeImage image, int x, int y, int width, int height) {
        for(int i = x; i < width; ++i) {
            for(int j = y; j < height; ++j) {
                int k = image.getPixelRGBA(i, j);
                if ((k >> 24 & 255) < 128) {
                    return;
                }
            }
        }

        for(int l = x; l < width; ++l) {
            for(int i1 = y; i1 < height; ++i1) {
                image.setPixelRGBA(l, i1, image.getPixelRGBA(l, i1) & 16777215);
            }
        }

    }

    private static void setAreaOpaque(NativeImage image, int x, int y, int width, int height) {
        for(int i = x; i < width; ++i) {
            for(int j = y; j < height; ++j) {
                image.setPixelRGBA(i, j, image.getPixelRGBA(i, j) | -16777216);
            }
        }

    }
}
