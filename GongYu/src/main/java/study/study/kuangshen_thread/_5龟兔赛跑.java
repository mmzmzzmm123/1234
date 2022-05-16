package study.study.kuangshen_thread;

/*
1．首先来个赛道距离，然后要离终点越来越近
2．判断比赛是否结束
3.打印出胜利者
4．龟兔赛跑开始
5．故事中是乌龟赢的，兔子需要睡觉，所以我们来模拟兔子睡觉
6.终于，乌龟赢得比赛
 **/
class Race implements Runnable {
    private static String winner;//用static，保证只有一个胜利者

    public static void main(String[] args) {
        Race Rubbit = new Race();
        Race tortise = new Race();
        new Thread(Rubbit, "兔子").start();
        new Thread(tortise, "乌龟").start();
    }

    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            //模拟兔子休息,每10步休息一下
            if (Thread.currentThread().getName().equals("兔子") && i % 10 == 0)//注意不要用==
            {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //判断比赛是否结束
            boolean flag = gameOver(i);
            //如果比赛结束，停止程序
            if (flag) {
                break;
            }

            System.out.println(Thread.currentThread().getName() + "-->跑了" + i + "步");
        }
    }

    //判断是否完成比赛
    private boolean gameOver(int steps) {
        if (winner != null) {
            return true;
        }
        {
            if (steps >= 1000) {
                winner = Thread.currentThread().getName();
                System.out.println("winneer is" + winner);
                return true;
            }
        }
        return false;
    }
}


