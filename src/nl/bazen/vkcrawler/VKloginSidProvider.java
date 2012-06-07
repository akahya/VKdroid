package nl.bazen.vkcrawler;

public class VKloginSidProvider extends VKsidProvider {

	public VKloginSidProvider() {
		super(true);
	}

	@Override
	protected VKsid getSID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String onInit() {
		return "Logging you in...";
	}

}
