//
// Created by Ha on 16. 1. 13..
//

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

// 트위터 stream API를 이용하여 실시간 트윗중에 특정 문자열을 필터링하여 crawling 하는 프로그램
// 아래 TOKEN과 KEY를 채워 넣어야 함.
public class Main {
    private static final String ACCESS_TOKEN = "";
    private static final String ACCESS_SECRET = "";
    private static final String CONSUMER_KEY = "";
    private static final String CONSUMER_SECRET = "";

    public static void main(String[] args) {

        // TOA 설정
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_SECRET);

        // 설정한 TOA로 TwitterStream 객체 생성
        TwitterStream twitterstream = new TwitterStreamFactory(cb.build()).getInstance();

        // 리스너 생성 - 유저이름, 장소, 트위터ID, 트윗내용을 가져와서 출력한다.
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                User user = status.getUser();

                // gets username
                String username = status.getUser().getScreenName();
                println("Username : " + username);

                String location = user.getLocation();
                println("Location : " + location);

                long tweetID = status.getId();
                println("TweetID : " + tweetID);

                String content = status.getText();
                println("Content : " + content + "\n");
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

            @Override
            public void onTrackLimitationNotice(int i) {}

            @Override
            public void onScrubGeo(long l, long l1) {}

            @Override
            public void onStallWarning(StallWarning stallWarning) {}

            @Override
            public void onException(Exception e) {}
        };

        // 필터 생성
        FilterQuery fq = new FilterQuery();

        // 필터링할 문자열
        String keywords[] = {"obama", "박근혜"};

        // 필터에 문자열 추가
        fq.track(keywords);

        // TwitterStream에 리스너와 필터 적용
        twitterstream.addListener(listener);
        twitterstream.filter(fq);


    }

    public static void println(String s){
        System.out.println(s);
    }

    public static void print(String s){
        System.out.print(s);
    }
}
