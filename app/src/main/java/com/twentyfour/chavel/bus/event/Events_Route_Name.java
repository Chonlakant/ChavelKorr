package com.twentyfour.chavel.bus.event;

public class Events_Route_Name {


	public static class Events_RoutNameFragmentMessage {

		private String message;

		public Events_RoutNameFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
