package com.twentyfour.chavel.bus.event;

public class Events_Route_Activity {


	public static class Events_ActivityFragmentMessage {

		private String message;

		public Events_ActivityFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
