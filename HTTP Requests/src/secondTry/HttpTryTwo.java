package secondTry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HttpTryTwo {
	public static void main(String[] args) throws Exception {
		getArticles("latest/", 1);
//		List<Article> articles = new ArrayList<Article>();
//		Parser p = new Parser();
//	
//        URL url = new URL("http://morningsignout.com/category/humanities");
//        URLConnection c = url.openConnection();
//        BufferedReader in = new BufferedReader(
//                                new InputStreamReader(
//                                c.getInputStream()));
//        String inputLine;
//        boolean inContent = false;
//        int closeDiv = 0, ind = 0;
//        
//        while ((inputLine = in.readLine()) != null) {
//        	if (inputLine.contains("<div class=\"content__post__info\">")) {
//        		articles.add(new Article());
//        		inContent = true;
//        	}
//        	
//        	if (inContent) {
//	        	if (inputLine.contains("<h1>")) {
//	        		String title = p.getTitle(inputLine),
//	        				link = p.getLink(inputLine);
//	        		
//	        		articles.get(ind).setTitle(title);
//	        		articles.get(ind).setLink(link);
//	        	} else if (inputLine.trim().contains("<img")) {
//	        		String imageURL = p.getImageURL(inputLine);
////		        		System.out.println(inputLine.trim());
//	        		
//	        		articles.get(ind).setImageURL(imageURL);
//	        	} else if (inputLine.contains("<p>")) {
//	        		String description = p.getDescription(inputLine);
//	        		articles.get(ind).setDescription(description);
//	        	}
//	        	
//	        	if (inputLine.trim().equals("</div>")) closeDiv++;
//
//	        	if (closeDiv == 2) {
//	        		closeDiv = 0;
//	        		inContent = false;
//	        		ind++;
////		        		System.out.println();
//	        	}
//        	}
//
////        		System.out.println(inputLine.trim());
//    		
//        }
//        in.close();
//        
//        for (int i = 0; i < articles.size(); i++)
//        	System.out.println(articles.get(i).getImageURL());
    }
	static List<Article> getArticles(String arg, int pageNum) {
        // String arg is "research", "wellness", "humanities", etc.
        // For getting article titles, descriptions, and images. See class Article
        Parser p = new Parser();
        String urlPath = "http://morningsignout.com/category/" + arg + "page/" + pageNum;
        ArrayList<Article> articlesList = null;

        BufferedReader in = null;
        HttpURLConnection c = null; // Done because of tutorial

        try {
            // Open connection to
            URL url = new URL(urlPath);
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();

            if (c.getInputStream() == null) return null; // Stream was null, why?

            in = new BufferedReader(new InputStreamReader(c.getInputStream() ) );
            String inputLine;

            // For parsing the html
            boolean inContent = false; // If in <h1> tags, need to wait 2 tags before
            int closeDiv = 0, ind = 0; // counts </div> tags, ind is index of articlesList

            articlesList = new ArrayList<Article>();

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("<div class=\"content__post__info\">")) {
                    articlesList.add(new Article());
                    inContent = true;
                }

                if (inContent) {
                    // Title & Link of article
                    if (inputLine.contains("<h1>")) {
                        String title = p.getTitle(inputLine),
                                link = p.getLink(inputLine);

                        articlesList.get(ind).setTitle(title);
                        articlesList.get(ind).setLink(link);
                    } // Image URL and/or Author
                    else if (inputLine.trim().contains("<img") || inputLine.trim().contains("<h2>")) {
                        if (inputLine.trim().contains("<img")) {
                            String imageURL = p.getImageURL(inputLine);
                            articlesList.get(ind).setImageURL(imageURL);
                        }

                        if (inputLine.trim().contains("<h2>")) {
                            String author = p.getAuthor(inputLine);
                            articlesList.get(ind).setAuthor(author);
                        }

                        // convert string to bitmap then feed to each article
//                        Bitmap image = downloadBitmap(imageURL);
                    }
                    // Description of article
                    else if (inputLine.contains("<p>")) {
                        String description = p.getDescription(inputLine);
                        articlesList.get(ind).setDescription(description);
                    }

                    if (inputLine.trim().equals("</div>")) closeDiv++;
                    if (closeDiv == 2) {
                        closeDiv = 0;
                        inContent = false;
                        ind++;
                        // System.out.println();
                    }
                }

                //        		System.out.println(inputLine.trim());

            }
            in.close();
//	        for (int i = 0; i < articlesList.size(); i++)
//	        	Log.e("FetchListArticlesTask", articlesList.get(i).getDescription());

            // If buffer was empty, no items in list, so website has no articles for some reason.
            return articlesList.isEmpty() ? null : articlesList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                	e.printStackTrace();
                    System.exit(-1);
                }
            }
        }

        return null; // Exiting try/catch likely means error occurred.
    }
}
