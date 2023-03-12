class TripRecord {

	private String pickupDateTime;
	private GPScoord pickupLocation;
	private GPScoord dropoffLocation;
	private float tripDistance;

	public TripRecord(String pickupDateTime, GPScoord pickupLocation, GPScoord dropoffLocation, float tripDistance) {
		this.pickupDateTime = pickupDateTime;
		this.pickupLocation = pickupLocation;
		this.dropoffLocation = dropoffLocation;
		this.tripDistance = tripDistance;
	}

	protected String getPickupDateTime() {
		return this.pickupDateTime;
	}

	protected GPScoord getPickupLocation() {
		return this.pickupLocation;
	}

	protected GPScoord getDropoffLocation() {
		return this.dropoffLocation;
	}

	protected float getTripDistance() {
		return this.tripDistance;
	}

}
