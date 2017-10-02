package com.twentyfour.chavel.bus.event;

public class Events_Route_Travel {


	public static class Events_TravelFragmentMessage {

		private String message;

		public Events_TravelFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
