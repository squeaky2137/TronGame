package game;

import packets.Death;
import packets.PlayerMove;
import packets.SelfDeath;
import packets.SelfMove;
import server.BaseServer;
import server.Connection;
import server.ConnectionHandler;

public class Game implements Runnable {
    private BaseServer server;
    private boolean started = false;

    public Game(BaseServer server) {
        this.server = server;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        started = true;
        while (started) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectionLoop:for(int i =0;i<ConnectionHandler.connections.size();i++) {
//                System.out.println("moving player " + i);

                Connection c = ConnectionHandler.connections.get(i);
                Player player = c.player;
                if (player.isAlive()) {
                    player.move();
                    if (!bounds(player)) {
                        player.setAlive(false);
                        Death death = new Death();
                        death.id = c.id;
                        death.alive = false;
                        server.sendToAllExcept(death, c.id);
                        SelfDeath selfDeath = new SelfDeath();
                        selfDeath.id = c.id;
                        selfDeath.alive = false;
                        c.sendObject(selfDeath);
                        continue;
                    }

//                    for(int j =0;j<ConnectionHandler.connections.size();j++) {
//                        Connection c2 = ConnectionHandler.connections.get(j);
//                        Player player2 = c2.player;
//                        if(c2==c) continue;
//                        if (player2.isAlive()) {
//                            if (player.isColliding(player2)) {
//                                player.setAlive(false);
//                                Death death = new Death();
//                                death.id = c.id;
//                                death.alive = false;
//                                server.sendToAllExcept(death, c.id);
//                                SelfDeath selfDeath = new SelfDeath();
//                                selfDeath.id = c.id;
//                                selfDeath.alive = false;
//                                c.sendObject(selfDeath);
//                                continue connectionLoop;
//                            }
//                        }
//                    }


                    System.out.println(ConnectionHandler.connections.keySet());
                    PlayerMove playerMove = new PlayerMove();
                    playerMove.id = c.id;
                    server.sendToAllExcept(playerMove, c.id);
                    SelfMove selfMove = new SelfMove();
                    selfMove.id = c.id;
//                    System.out.println(selfMove);
                    c.sendObject(selfMove);
                }
            }
        }
    }

    public void stop() {
        started = false;
    }

    public boolean bounds(Player p)
    {
        return p.getX() < 800 && p.getX() >= 0 && p.getY() < 800 && p.getY() >= 0;
    }
}
