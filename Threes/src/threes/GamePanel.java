package threes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class GamePanel extends JPanel implements ThreesView
{
	private ThreesModel data;
	private ThreesMain control;

	protected JPanel TabPanel;

	private Point mouse1;

	private JLabel score;

	private JMenu menu;
	
	private PopupCirculaire pop;

	public GamePanel(ThreesMain controller)
	{
		super();
		setFocusable(true);
		setVisible(true);

		control = controller;
		data = controller.getModel();
		score = new JLabel();

		setLayout(new BorderLayout(5, 5));

		TabPanel = new JPanel();

		pop = new PopupCirculaire(100, 100, -45);
		add(pop);
		pop.addItem("HAUT");
		pop.addItem("BAS");
		pop.addItem("GAUCHE");
		pop.addItem("DROITE");
		pop.setVisible(false);
		
		/*
		menu = new JMenu();
		menu.insert("Haut", 0);
		menu.insert("Bas", 1);
		menu.insert("Gauche", 2);
		menu.insert("Droite", 3);
		menu.setEnabled(true);
		
		menu.setPopupMenuVisible(false);
		add(menu);*/

		TabPanel.setLayout(new GridLayout(4,4));
		TabPanel.setVisible(true);

		add(TabPanel, BorderLayout.CENTER);
		add(score, BorderLayout.SOUTH);

		addKeyListener(keyMove);
		addMouseListener(mouseClick);
		
		/*menu.getItem(0).addActionListener(menuHaut);
		menu.getItem(1).addActionListener(menuBas);
		menu.getItem(2).addActionListener(menuGauche);
		menu.getItem(3).addActionListener(menuDroite);*/

		((GridLayout) TabPanel.getLayout()).setHgap(5);
		((GridLayout) TabPanel.getLayout()).setVgap(5);
		TabPanel.setBackground(new Color(184,216,216));
		TabPanel.setOpaque(true);
	}

	public void initPartie()
	{
		requestFocus();
	}

	public void afficherMenu(int x, int y)
	{
		pop.setLocation(x-pop.getWidth()/2, y-pop.getHeight()/2);
		pop.setVisible(true);
		pop.repaint();
	}

	public void update()
	{
		int x, y;
		for(x =0;x<4;x++)
		{
			for(y=0;y<4;y++)
			{
				setCase(x, y, data.getValue(x, y));
			}
		}
		score.setText("Score : " + Integer.toString(data.getScore()));
		requestFocus();
	}

	public abstract void setCase(int x, int y, int value);

	public JMenu getMenu()
	{
		return this.menu;
	}

	public ActionListener menuHaut = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(data.getLoss())
			{
				control.perdu();
			}
			else
			{
				control.moveUp();
			}
		}
	};

	public ActionListener menuBas = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{

			if(data.getLoss())
			{
				control.perdu();
			}
			else
			{
				control.moveDown();
			}
		}
	};

	public ActionListener menuDroite = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(data.getLoss())
			{
				control.perdu();
			}
			else
			{
				control.moveRight();
			}
		}
	};

	public ActionListener menuGauche = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if(data.getLoss())
			{
				control.perdu();
			}
			else
			{
				control.moveLeft();
			}
		}
	};

	public MouseListener mouseClick = new MouseListener()
	{
		@Override
		public void mouseReleased(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				Point mouse2 = e.getPoint();
				int dx = mouse1.x - mouse2.x;
				int dy = mouse1.y - mouse2.y;

				if(Math.abs(dx) < 10 && Math.abs(dy) < 10) // Pour éviter de prendre en compte les mouvements trop petits
				{
					return;
				}
				if(Math.abs(dy) > Math.abs(dx))
				{
					if(dy > 0)
					{
						control.moveUp();
					}
					else
					{
						control.moveDown();
					}
				}
				else
				{
					if(dx > 0)
					{
						control.moveLeft();
					}
					else
					{
						control.moveRight();
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			if(data.getLoss())
			{
				control.perdu();
			}

			else if(e.getButton() == MouseEvent.BUTTON1)
			{
				mouse1 = e.getPoint();
			}
		}

		@Override
		public void mouseExited(MouseEvent e){}

		@Override
		public void mouseEntered(MouseEvent e){}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			/*if(e.getButton() == MouseEvent.BUTTON3)
			{
			    mc.setLocation(e.getX(),e.getY());
			    mc.setVisible(true);
			}*/
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				afficherMenu(e.getX(), e.getY());
			}
		}
	};

	public KeyListener keyMove = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if(data.getLoss())
			{
				control.perdu();
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				control.moveUp();
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				control.moveDown();
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				control.moveLeft();
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				control.moveRight();
			}
		}
	};

}