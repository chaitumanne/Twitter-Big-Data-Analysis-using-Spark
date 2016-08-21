/**
 * Created by VARSHA-PC on 2/12/2016.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TweetsCollection {
    public static long count = 0;
    public static void main(String[] args) throws IOException, TwitterException {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled (true);
        configurationBuilder.setJSONStoreEnabled(true);
        configurationBuilder.setOAuthConsumerKey("TzwUekH6BoHMo3qYBwntZ0LHV");
        configurationBuilder.setOAuthConsumerSecret("u16u9XruWMq8niThK3qqUwY4w3j0TafmZh2DAE5mKktSBbGirL");
        configurationBuilder.setOAuthAccessToken("151427402-YuZOHlBgOzctVhQ37otknpGkOZgi3U8cEF2EJnvW");
        configurationBuilder.setOAuthAccessTokenSecret("sYRjs1kedmvA52Rnfg98z3Wz1tKcPnqs5poJsBOorSptg");
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();

        StatusListener sListner = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter("E:\\tweets.txt",true));
                    String tweetJson = TwitterObjectFactory.getRawJSON(status);
                    bw.write(tweetJson);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    try {
                        if (bw != null) {
                            bw.flush();
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Count: " + count);
                count++;

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }
        };

        FilterQuery filterQuery = new FilterQuery();
        String keywords[] = {"cricket","worldcup","nba","soccer","game","sport","casino","club","cards","baseball","lasvegas","music","action","tv shows","films","netflix","tennis"};
        filterQuery.track(keywords);
        twitterStream.addListener(sListner);
        twitterStream.filter(filterQuery);

    }
}
