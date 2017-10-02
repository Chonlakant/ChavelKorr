package com.twentyfour.chavel.bus.event;

public class Events_Route_Suggestion {


	public static class Events_RoutSuggestionFragmentMessage {

		private String message;

		public Events_RoutSuggestionFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
