package uoft.csc207.gameapplication.Activities.GameActivities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import uoft.csc207.gameapplication.Games.GameDriver;
import uoft.csc207.gameapplication.Utility.GameRequestService.CallBack;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.UserVerificationServices.LoginService;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Score;
import uoft.csc207.gameapplication.Utility.GameRequestService.Models.Token;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.ScorePosterService;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.StagePosterService;
import uoft.csc207.gameapplication.Utility.GameRequestService.RestApiServices.GameDataService.TimePlayedService;


public class GameView extends View {
    private GameDriver gameDriver;
    private Timer timer;
    private boolean scorePosted = false;
    private boolean stageUpdated = false;
    private boolean timeUpdated = false;
    private String stage = "0";
    private String game = "WrapperGame";

    private ScorePosterService scorePosterService;
    private StagePosterService stagePosterService;
    private TimePlayedService timePlayedService;

    private long startTime;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scorePosterService = new ScorePosterService();
        scorePosterService.setContext(context);
        stagePosterService = new StagePosterService();
        stagePosterService.setContext(context);
        timePlayedService = new TimePlayedService();
        timePlayedService.setContext(context);
    }

    /**
     * Starts running the game.
     */
    public void start() {
        startTime = System.currentTimeMillis();
        gameDriver.start();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameDriver.getGameIsOver()) {
                    postScores();
                    postStage();
                    postTimePlayed();
                    stop();
                } else {
                    gameDriver.timeUpdate();
                    invalidate();
                }
            }
        }, 0, 30);
    }

    /**
     * Stops the game and thread-related processes.
     */
    public void stop() {
        gameDriver.stop();
        timer.cancel();
        timer.purge();
    }

    private void postTimePlayed() {
        if (!timeUpdated) {
            Token token = LoginService.getLoginToken();

            timePlayedService.updateTimePlayed(token, System.currentTimeMillis() - startTime, new CallBack() {

                @Override
                public void onSuccess() {
                    System.out.println("successful Request time updated");
                }

                @Override
                public void onFailure() {
                    System.out.println("Failed Request updating time");
                }

                @Override
                public void onWait() {
                }
            });
            timeUpdated = true;
        }
    }

    private void postScores() {
        if (!scorePosted) {
            Token token = LoginService.getLoginToken();

            Score score = new Score();
            score.setScore(String.valueOf(gameDriver.getPoints()));
            score.setUsername(token.getUsername());

            scorePosterService.postScore(token, score, game, new CallBack() {
                @Override
                public void onSuccess() {
                    System.out.println("successful Request scores posted");
                }

                @Override
                public void onFailure() {
                    System.out.println("Failed Request posting scores");
                }

                @Override
                public void onWait() {
                }
            });
            scorePosted = true;
        }
    }

    private void postStage() {
        if (!stageUpdated) {
            Token token = LoginService.getLoginToken();

            stagePosterService.postStage(token, stage, new CallBack() {
                @Override
                public void onSuccess() {
                    System.out.println("successful Request posted stage");
                }

                @Override
                public void onFailure() {
                    System.out.println("Failed Request posting stage");
                }

                @Override
                public void onWait() {
                }
            });
            stageUpdated = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        gameDriver.draw(canvas);
    }

    /**
     * Registers touch events
     * @param event a touch event
     * @return true if and only the touch was processed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                gameDriver.touchStart(x, y);
                return true;
            case MotionEvent.ACTION_MOVE :
                gameDriver.touchMove(x, y);
                return true;
            case MotionEvent.ACTION_UP :
                gameDriver.touchUp();
                return true;
        }
        return false;
    }

    public void setDriver(GameDriver driver) {
        this.gameDriver = driver;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setGame(String game) {
        this.game = game;
    }
}










