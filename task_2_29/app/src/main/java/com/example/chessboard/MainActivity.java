package com.example.chessboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons;
    private String[][] colors;
    private final int side = 8;
    private final int whiteColor = Color.WHITE;
    private final String greenColor = "#98FB98",
            gridColor1 = "#FFCE9E", gridColor2 = "#D18B47";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGui();
    }

    public void buildGui(){//by code
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x/side;
        GridLayout newGridLayout = new GridLayout(this);
        newGridLayout.setColumnCount(side);
        newGridLayout.setRowCount(side);

        buttons = new Button[side][side];
        colors = new String[side][side];
        ButtonHandler bh = new ButtonHandler();
        boolean color = true;   //true for WHITE, false for BLACK
        for (int i=0; i<side; i++){
            color = i%2==0;
            for (int j=0; j<side; j++){
                buttons[i][j] = new Button(this);
                buttons[i][j].setOnClickListener(bh);
                //set color
                colors[i][j] = color? gridColor1:gridColor2;
                buttons[i][j].setBackgroundColor(Color.parseColor(colors[i][j]));
                color = !color;

                newGridLayout.addView(buttons[i][j], w, w);
            }
        }
        setContentView(newGridLayout);

        setPieces();
    }

    private void setPieces(){
        for (int i=1; i<side; i+=5){
            for (int j=0; j<side; j++){
                buttons[i][j].setText("P");
            }
        }

        buttons[0][0].setText("R");
        buttons[0][1].setText("K");
        buttons[0][2].setText("B");
        buttons[0][3].setText("Q");
        buttons[0][4].setText("KG");
        buttons[0][5].setText("B");
        buttons[0][6].setText("K");
        buttons[0][7].setText("R");
        buttons[7][0].setText("R");
        buttons[7][1].setText("K");
        buttons[7][2].setText("B");
        buttons[7][3].setText("Q");
        buttons[7][4].setText("KG");
        buttons[7][5].setText("B");
        buttons[7][6].setText("K");
        buttons[7][7].setText("R");

        for (int i=0; i<2; i++){
            for (int j=0; j<side; j++){
                buttons[i][j].setTextColor(Color.BLACK);
            }
        }

        for (int i=6; i<side; i++){
            for (int j=0; j<side; j++){
                buttons[i][j].setTextColor(Color.WHITE);
            }
        }
    }

    private void showWhereCanMove(int row, int col){
        Log.w("MainActivity", "Inside update: " + row + ", " + col);
        String pieceName = buttons[row][col].getText().toString();
        resetColors();
        switch(pieceName){
            case "P":{
                showPawnMove(row, col);break;
            }
            case "R":{
                showRookMove(row, col);break;
            }
            case "K":{
                showKnightMove(row, col);break;
            }
            case "B":{
                showBishopMove(row, col);break;
            }
            case "Q":{
                showQueenMove(row, col);break;
            }
            case "KG":{
                showKingMove(row, col);break;
            }
            default:break;
        }
    }

    private void showPawnMove(int row, int col){
        int step = 1;
        if (buttons[row][col].getCurrentTextColor() == whiteColor)
            step = -1;

        //if (row+step<side && row+step>=0 && ifBlank(buttons[row][col]))
        setGreen(row+step, col);
    }

    private void showRookMove(int row, int col){
        for (int i=0; i<side; i++){
            setGreen(i, col);
        }
        for (int j=0; j<side; j++){
            setGreen(row, j);
        }
        buttons[row][col].setBackgroundColor(Color.parseColor(colors[row][col]));
    }

    private void showKnightMove(int row, int col){
        if (row>=2){
            if (col>=1)
                setGreen(row-2,col-1);
            if (col+1<side)
                setGreen(row-2,col+1);
        }
        if (row+2<side){
            if (col>=1)
                setGreen(row+2,col-1);
            if (col+1<side)
                setGreen(row+2,col+1);
        }
        if (col>=2){
            if (row>=1)
                setGreen(row-1,col-2);
            if (row+1<side)
                setGreen(row+1,col-2);
        }
        if (col+2<side){
            if (row>=1)
                setGreen(row-1,col+2);
            if (row+1<side)
                setGreen(row+1,col+2);
        }
    }

    private void showBishopMove(int row, int col){
        for (int i=row, j=col; i<side && j<side; i++, j++){
            setGreen(i, j);
        }
        for (int i=row, j=col; i<side && j>=0; i++, j--){
            setGreen(i, j);
        }
        for (int i=row, j=col; i>=0 && j<side; i--, j++){
            setGreen(i, j);
        }
        for (int i=row, j=col; i>=0 && j>=0; i--, j--){
            setGreen(i, j);
        }
    }

    private void showQueenMove(int row, int col){
        showRookMove(row, col);
        showBishopMove(row, col);
    }

    private void showKingMove(int row, int col){
        for (int i=row-1; i<=row+1; i++){
            for (int j=col-1; j<=col+1; j++){
                if (i>=0 && j>=0 && i<side && j<side)
                    setGreen(i, j);
            }
        }
        buttons[row][col].setBackgroundColor(Color.parseColor(colors[row][col]));
    }

    private void setGreen(int row, int col){
        buttons[row][col].setBackgroundColor(Color.parseColor(greenColor));
    }

    private void resetColors(){
        for (int i=0; i<side; i++){
            for (int j=0; j<side; j++){
                buttons[i][j].setBackgroundColor(Color.parseColor(colors[i][j]));
            }
        }
    }

    private boolean ifBlank(Button b){
        return b.getText().length()==0;
    }

    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Log.w("MainActivity", "Inside onClick, v = " + v);
            for (int i=0; i<side; i++){
                for (int j=0; j<side; j++){
                    if (v == buttons[i][j])
                        showWhereCanMove(i, j);
                }
            }
        }
    }
}
