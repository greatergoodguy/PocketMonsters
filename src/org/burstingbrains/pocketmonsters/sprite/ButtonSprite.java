package org.burstingbrains.pocketmonsters.sprite;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.burstingbrains.sharedlibs.handler.IButtonHandler;

public class ButtonSprite extends Sprite{
	
	private static final DummyButtonHandler dummyButtonHandler = new DummyButtonHandler();
	
	IButtonHandler buttonHandler;
	
	public ButtonSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.buttonHandler = dummyButtonHandler;
	}
	
	public ButtonSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, IButtonHandler buttonHandler) {
		this(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.buttonHandler = buttonHandler;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		
		switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				this.setScale(1.3f);
				break;
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_UP:
				buttonHandler.onButtonUp();
				this.setScale(1);
				break;
		}
		return true;
	}
	
	private static class DummyButtonHandler implements IButtonHandler{

		@Override
		public void onButtonUp() {
			// This method is blank since this is a dummy handler
		}
	}

}
