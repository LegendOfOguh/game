package unb.cic.poo.game2d;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import android.graphics.Color;
import org.andengine.util.debug.Debug;


public class ResourceManager {
	private static final ResourceManager INSTANCE = new ResourceManager();
	  
	// Todas as imagens animadas devem ser declaradas com Tiled, especificando quantas vers�es do sprite tem
	// na textura especificada. Tamb�m tomar cuidado para elas estarem bem alinhadas.
	public ITextureRegion introTextureRegion;
	BitmapTextureAtlas introTexture;
	
	public ITextureRegion menuBackgroundTextureRegion;
	public ITextureRegion playTextureRegion;
	public ITextureRegion optionsTextureRegion;
	BuildableBitmapTextureAtlas menuTexture;
	
	/*Player*/
	BitmapTextureAtlas playerTexture;
	public static ITiledTextureRegion playerTextureRegion;
	
	/*Enemys*/
	BitmapTextureAtlas walkerTexture;
	public static ITiledTextureRegion walkerTextureRegion;
	BitmapTextureAtlas shooterTexture;
	public static ITiledTextureRegion shooterTextureRegion;
	BitmapTextureAtlas laserTexture;
	public static ITiledTextureRegion laserTextureRegion;
	
	/*Bullets*/
	BitmapTextureAtlas bulletTexture;
	public static ITextureRegion bulletTextureRegion;
	BitmapTextureAtlas laserBulletTexture;
	public static ITextureRegion laserBulletTextureRegion;
	
	/*Backgrounds*/
	BitmapTextureAtlas backgroundTexture;
	public static ITextureRegion backgroundTextureRegion;
	
	/*Icons*/
	BitmapTextureAtlas gameoverTexture;
	public ITextureRegion gameoverTextureRegion;
	
	  //common objects
	  public GameActivity activity;
	  public Engine engine;
	  public Camera camera = GameActivity.mCamera;
	  public VertexBufferObjectManager vbom;
	  
	  /*Enemys dimens�es de anima��o*/
	  private static int WALKER_COLUMN = 6, WALKER_ROW = 1;
	  private static int SHOOTER_COLUMN = 9, SHOOTER_ROW = 1;
	  private static int LASER_COLUMN = 5, LASER_ROW = 1;
	  private static int PLAYER_COLUMN = 8, PLAYER_ROW = 1;
	  
	  public Font font;
	  
	  public static ResourceManager getInstance() {
		  return INSTANCE;
	  }
	  
	  public void prepare(GameActivity activity) {
			this.activity = activity;
			this.engine = activity.getEngine();
	  }

	  public synchronized void loadIntro() {
		  	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		  	introTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		  	introTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(introTexture, activity, "logo.png", 0, 0);
		  	introTexture.load();
	  }
	  
	  public synchronized void unloadIntro() {
		  	introTexture.unload();
		  	introTextureRegion = null;
	  }
	  
	  public synchronized void loadFonts(){
		  	FontFactory.setAssetBasePath("font/");
		  	final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font_loading.ttf", 70, true, Color.WHITE, 2, Color.BLACK);
		    font.load();
	  }
	  
	  public synchronized void unloadFonts() {
		  	font.unload();
	  }
	  	  
	  public synchronized void loadMenu() {
		  	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		  	menuTexture = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
		 	menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, activity, "background.bmp");
		 	playTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, activity, "play.png");
		 	optionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, activity, "options.png");
		 	       
		 	try {
		 	    this.menuTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		 	    this.menuTexture.load();
		 	} 
		 	catch (final TextureAtlasBuilderException e) {
		 	        Debug.e(e);
		 	}
	  }
	  
	  public synchronized void unloadMenu() {
		  menuTexture.unload();
		  menuBackgroundTextureRegion = null;
		  playTextureRegion = null;
		  optionsTextureRegion = null;
	  }
	  
	 // Classe para carregar as texturas da pasta asset
	 public synchronized void loadGameTextures(){
			  // Set our game assets folder in "assets/gfx/game/"
		 
		 	/* Ao declarar novas sprites, acoplar juntamente aos antigos aqui, devido ao caminho da pasta assets*/
		 	/* PASTA JOGADOR*/
		 	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/player/");
		 	
		 	// Ao carregar imagens, colocar pot�ncias de 2 maiores do que a resolu��o da mesma
		 	// Evitar colocar imagens maiores que 1024
		 		 	
		 	
		 	playerTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1536, 124, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			playerTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(playerTexture, activity,"player_animated.png", 0, 0, PLAYER_COLUMN, PLAYER_ROW);
			playerTexture.load();
			
			/* PASTA INIMIGOS*/
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/enemys/");
			
			walkerTexture = new BitmapTextureAtlas(engine.getTextureManager(), 512, 124, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			walkerTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(walkerTexture, activity,"walker_animation.png", 0, 0, WALKER_COLUMN, WALKER_ROW);
			walkerTexture.load();
			
			shooterTexture = new BitmapTextureAtlas(engine.getTextureManager(), 512, 124, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			shooterTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(shooterTexture, activity,"shooter_animation.png", 0, 0, SHOOTER_COLUMN, SHOOTER_ROW);
			shooterTexture.load();
			
			laserTexture = new BitmapTextureAtlas(engine.getTextureManager(), 512, 124, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			laserTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(laserTexture, activity,"laser_animation.png", 0, 0, LASER_COLUMN, LASER_ROW);
			laserTexture.load();
			
			
			/* PASTA BALAS*/
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/bullets/");
	 
			bulletTexture = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32);
			bulletTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(bulletTexture, activity,"fire.png",0,0);
			bulletTexture.load();
			
			laserBulletTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1280, 32);
			laserBulletTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(laserBulletTexture, activity,"laserbullet.png",0,0);
			laserBulletTexture.load();
			
			/* PASTA BACKGROUNDS*/
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/backgrounds/");
			backgroundTexture = new BitmapTextureAtlas(engine.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
	        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
	                .createFromAsset(backgroundTexture, activity, "background.png",0,0);
	        backgroundTexture.load();

	        /* PASTA ICONS*/
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/icons/");
	        gameoverTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
	        gameoverTextureRegion = BitmapTextureAtlasTextureRegionFactory
	                .createFromAsset(gameoverTexture, activity, "gameover.png",0,0);
	        gameoverTexture.load();
	 }
	 
	 public synchronized void unloadEnemys(){
		 walkerTexture.unload(); walkerTextureRegion = null;
		 shooterTexture.unload(); shooterTextureRegion = null;
		 laserTexture.unload(); laserTextureRegion = null;
	 }
	 
	 public synchronized void unloadGameTextures() {
		  playerTexture.unload(); playerTextureRegion = null;
		  unloadEnemys();
		  bulletTexture.unload(); bulletTextureRegion = null;
		  laserBulletTexture.unload(); laserTextureRegion = null;
		  backgroundTexture.unload(); backgroundTextureRegion = null;
		  gameoverTexture.unload(); gameoverTextureRegion = null;
	  }
}
