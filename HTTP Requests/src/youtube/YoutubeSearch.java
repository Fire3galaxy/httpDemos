package youtube;

import java.io.IOException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.GeoPoint;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;


public class YoutubeSearch {

	public static void main(String[] args) {
		YouTube youtube = new YouTube.Builder(new NetHttpTransport(), 
				new JacksonFactory(), new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
					}
			
		}).setApplicationName("youtube-search").build();
	}

}
