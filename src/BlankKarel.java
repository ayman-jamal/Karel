import stanford.karel.SuperKarel;
public class BlankKarel extends SuperKarel {
    public int stepsCounter = 0;
    // padding = num - ((num/4)*4)
    public void run() {
        setBeepersInBag(1000);
        int n = 0, m = 0;
        while (true) {
            if (facingEast()) {
                ++m;
                if (frontIsBlocked()) {
                    System.out.println("m = " + m);
                    turnLeft();
                }
            }
            if (facingNorth()) {
                ++n;
                if (frontIsBlocked()) {
                    System.out.println("n = " + n);
                    break;
                }
            }
            myMove();
        }
        if ( n>2 && m>2) {
            if (n % 2 == 0 && m % 2 == 0) {
                if ((n!=4||m!=6) && (n!=6 ||m!=4)) zigZagDivider(n, m, stepsCounter);
                else zigZagDivider(n, m, stepsCounter);

            }
            if (n%2!=0 && m%2!=0) {
                turnLeft();
                moveSteps((m / 2) + 1, stepsCounter);
                turnLeft();
                putWithMove(n, stepsCounter, 0, false, false);
                turnLeft();
                moveSteps((m / 2) + 1, stepsCounter);
                turnLeft();
                moveSteps((n / 2) + 1, stepsCounter);
                turnLeft();
                putWithMove(m, stepsCounter, 0, false,false);
                turnLeft();
                moveSteps((n / 2) + 1, stepsCounter);
                turnLeft();
            }
            if (n % 2 != 0 && m % 2 == 0) {
                boolean n3= false;
                int c2=1;
                if(n==3){
                    n3 = true;
                    c2=0;
                }
                V_zigZag(n, m, 0, c2, true,n3);
                turnLeft();
                moveSteps((m / 2), stepsCounter);
                turnLeft();
                moveSteps((n / 2) + 1, stepsCounter);
                turnLeft();
                putWithMove(m, stepsCounter, 0, false, false);
                turnRight();
                moveSteps((n / 2) + 1, stepsCounter);
                turnRight();
                moveSteps(m, stepsCounter);
                turnAround();
            }
            if (n % 2 == 0 && m % 2 != 0) {
                boolean m3= false;
                int c1=1;
                if(m==3){
                    m3 = true;
                    c1=0;
                }
                System.out.println("hi");
                turnLeft();
                moveSteps((m / 2) + 1, stepsCounter);
                turnLeft();
                putWithMove(n, stepsCounter, 0, false, false);
                turnRight();
                moveSteps((m / 2) + 1, stepsCounter);
                turnRight();
                moveSteps((n / 2), stepsCounter);
                turnRight();
                H_ZigZag(n, m, 0, c1, false, true, m3);
                turnLeft();
                moveSteps((n / 2) + 1, stepsCounter);
                turnLeft();
            }
        }else if (n==2 && m==2) specialZigZag(1);
        else if((n!=1 && m==1) || (n==1&&m!=1)){
            int num =0;
            if(n==1) {
                num = m;
                checkAnDivid(num,false, true);
            }
            else{
                num = n;
                checkAnDivid(num, true, true);
            }
        }else if((n==2 && m>6) || (n>6&&m==2)){
            int num =0;
            if(n==2) {
                num = m;
                checkAnDivid(num, false,false);
                myMove();
                turnLeft();
                moveSteps(num, stepsCounter);
                checkAnDivid(num, true,false);
                turnLeft();
            }else{
                num = n;
                checkAnDivid(num, true, false);
                turnAround();
                myMove();
                turnRight();
                moveSteps(num,stepsCounter);
                checkAnDivid(num,true, false);
            }
        }else if((n==2 && m<=6) || (n<=6&&m==2)) {
            float nm=0;
            if(m>2) nm =m;
            else if (n>2) nm=n;
            specialZigZag(nm);
        }
    }
    public void specialZigZag(float nm){
        float num=0;
        if(nm==1) num =1;
        else if(nm>1) num=(float) Math.floor(nm/2);
        for(int i=0;i<=num;i++) {
            putBeeper();
            turnLeft();
            myMove();
            turnLeft();
            if (nm == 6.0 && (i == Math.ceil(nm / 2)-1 ||i == Math.ceil(nm / 2)-2) && noBeepersPresent() ) putBeeper();
            myMove();
            if (nm == 3 && i == Math.ceil(nm / 2)-1) {
                turnLeft();
                break;
            }
            putBeeper();
            turnLeft();
            if (i == Math.ceil(nm / 2) - 1) break;
            if(nm>1) {
                move();
                turnRight();
                move();
                turnLeft();
                turnLeft();
            }
        }
    }
    public void myMove(){
        if(frontIsClear()){
            move();
            System.out.println("Karel steps : "+ ++stepsCounter);}
    }
    public void checkAnDivid(int num, boolean flagNgM, boolean oneCol){
        int den = 0;
        if (num>6){
            if(num%4==0) den=(num/4)-1;
            else {
                int temp = num - (num/4)*4;
                int num2 = num - temp;
                if ( num==7 ) den = 1;
                else den = (num2/4) - 1;
            }
            if(flagNgM) turnAround();
            else turnLeft();
            simpleDivider(num,den);

        }else if(num==2){
            turnAround();
            myMove();
            turnLeft();
        }else { //num<=6
            den =1;
            turnAround();
            simpleDivider(num, den);
        }
    }
    public void simpleDivider(int num, int den){
        int cnt = 0;
        int myDen = den+1;
        int padding = num - ((num/4)*4); // 11 - ((11/4)*4) = 11 - 8 = 3
        int tempNum= num;
        for(int j=num;j>0;j--) {
            if(tempNum==padding && num>7) {
                putBeeper();
                padding--;
            } else  if (num < 6 ) {
                if(j%2==0) putBeeper();
            }else if(myDen-cnt==(den+1) ) {
                putBeeper();
                myDen +=(den+1);
                cnt++;
            }else cnt++;
            if (frontIsBlocked()) break;
            myMove();
            tempNum--;
        }
        turnAround();
        turnRight();
    }
    public void leftRight(boolean flag){
        if(flag) turnLeft();
        else turnRight();
    }
    public void H_ZigZag(int n,int m, int c1, int c2, boolean flag, boolean zigZag, boolean num3){
        int myn      = (n/2);
        int mym      = (m/2);
        putWithMove(m, stepsCounter,c1, zigZag, num3);
        leftRight(!flag);
        myMove();
        if(n!=m && ((n%4!=0)|| (m%4!=0)) && ((m/2)+1)%7!=0) putBeeper();
        leftRight(!flag);
        putWithMove(m, stepsCounter, c2, zigZag, num3);
        if(n!=m && ((n%4!=0)||(m%4!=0)) && ((m/2)+1)%7!=0) putBeeper();
    }
    public void V_zigZag(int n, int m,int c1,int c2, boolean flag, boolean num3){
        int mym;
        if(flag) mym = (m/2);
        else mym = (m/2)+1;
        turnLeft();
        moveSteps(mym, stepsCounter);
        turnLeft();
        putWithMove(n, stepsCounter, c1, flag, num3);
        if(n!=m && ((n%4!=0)||(m%4!=0))) putBeeper();
        turnRight();
        myMove();
        turnRight();
        putWithMove(n, stepsCounter, c2, flag, num3);
        if(n!=m && ((n%4!=0) ||(m%4!=0))) putBeeper();
    }
    public void zigZagDivider(int n, int m,int stepsCounter){
        int myn      = (n/2);
        V_zigZag(n,m,0,0,true, false);
        turnLeft();
        moveSteps((m/2),stepsCounter);
        turnLeft();
        moveSteps((n/2),stepsCounter);
        turnLeft();
        H_ZigZag(n,m,0,0,true,true, false);
        turnLeft();
        moveSteps(myn,stepsCounter);
        turnLeft();
    }
    public void moveSteps(int steps,int stepsCounter){
        for(int i = steps;i>1;i--){
            myMove();
        }
    }
    public void putWithMove(int num, int stepsCounter, int count, boolean flag, boolean num3){
        for(int i =num;i>=0;i--){
            if(flag) {
                if (count % 2 == 0) {
                    if (noBeepersPresent()) putBeeper();
                }
                if(!num3) count++;
            }else{
                if(noBeepersPresent()) putBeeper();
            }
            if(frontIsBlocked()) break;
            myMove();
        }
    }
}