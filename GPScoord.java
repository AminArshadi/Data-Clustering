class GPScoord {

	private double lon;
	private double lat;

	public GPScoord(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}

	protected double getLon() {
		return this.lon;
	}

	protected double getLat() {
		return this.lat;
	}

	protected String print() {
		
		return this.lon + ":" + this.lat;
	}
}
