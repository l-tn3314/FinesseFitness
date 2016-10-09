//package com.example.finessefitness;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.DialogInterface;
//import android.os.Bundle;
//
//public class QuitDialog extends DialogFragment {
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        System.out.println(getActivity());
//        AlertDialog.Builder builder = new AlertDialog.Builder(savedInstanceState.);
//
//        builder.setTitle("Quit?");
//        builder.setMessage("Are you sure you want to quit this workout?");
//        builder.setNegativeButton(android.R.string.yes,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // user clicks the yes button
//                    }
//
//                });
//        builder.setPositiveButton(android.R.string.no,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // user clickes the no button
//                    }
//                });
//        Dialog create = builder.create();
//        create.show();
//        return create;
//    }
//}