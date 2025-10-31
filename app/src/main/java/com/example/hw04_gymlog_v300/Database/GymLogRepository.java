package com.example.hw04_gymlog_v300.Database;

import android.app.Application;
import android.util.Log;

import com.example.hw04_gymlog_v300.Database.entities.GymLog;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Marissa Benenati
 * <br>⋆.˚｡⋆⚘⭒⋆✴︎˚｡⋆
 * <br>COURSE: CST 338 - Software Design
 * <br>DATE: 10/30/2025
 * <br>ASSIGNMENT: GymLog
 */
public class GymLogRepository {

    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;
    public GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs(){
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return gymLogDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            Log.i("GymLogRepository.java", "Problem when getting all GymLogs");
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog){
        GymLogDatabase.databaseWriteExecutor.execute(()->
        {
            gymLogDAO.insert(gymLog);
        });
    }

}
