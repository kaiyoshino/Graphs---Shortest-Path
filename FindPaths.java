//Kai Yoshino
//6/1/15
//CSE373
//Assignment #5
//This program reads in a graph and prompts user for shortests paths in the graph
//Assumes that we do not have negative cost edges in the graph.

import java.util.*;
import java.io.*;


public class FindPaths {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
			System.exit(1);
		}

		MyGraph g = readGraph(args[0],args[1]);

		Scanner console = new Scanner(System.in);
		Collection<Vertex> v = g.vertices();
                Collection<Edge> e = g.edges();
		System.out.println("Vertices are "+v);
		System.out.println("Edges are "+e);
      
      // propmts the user repeatedly
		while(true) {
			System.out.print("Start vertex? ");
         Vertex[] ve = v.toArray(new Vertex[v.size()]);
			String s = console.nextLine();
         Vertex a = new Vertex("x");
         for (int i = 0; i < ve.length; i++) {
            if (ve[i].toString().equals(s)) {
                a = ve[i];
            } 
         }
			if(!v.contains(a)) {
				System.out.println("no such vertex");
				System.exit(0);
			}
			
			System.out.print("Destination vertex? ");
         String d = console.nextLine();
         Vertex b = new Vertex("x");
         for (int i = 0; i < ve.length; i++) {
            if (ve[i].toString().equals(d)) {
               b = ve[i];
            } 
         }
			if(!v.contains(b)) {
				System.out.println("no such vertex");
				System.exit(1);
			}
			Path p = g.shortestPath(a, b);
			System.out.println("Shortest path from X to Y: ");
         if (p == null) {
            System.out.println("does not exist");
         } else {
            Vertex[] path = p.vertices.toArray(new Vertex[p.vertices.size()]);
            for (int i = 0; i < path.length; i++) {
               System.out.print(path[i].toString() + " "); 
            }
            System.out.println();
            System.out.println(b.cost);
         }
		}
	}

	public static MyGraph readGraph(String f1, String f2) {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f1);
			System.exit(2);
		}

		Collection<Vertex> v = new ArrayList<Vertex>();
		while(s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		while(s.hasNext()) {
			try {
            Vertex a = new Vertex("x");
            Vertex b = new Vertex("x");
            Vertex[] ve = v.toArray(new Vertex[v.size()]);
            String next = s.next();
            for (int i = 0; i < ve.length; i++) {
               if (ve[i].toString().equals(next)) {
                  a = ve[i];
               } 
            }
            String next2 = s.next();
				for (int i = 0; i < ve.length; i++) {
               if (ve[i].toString().equals(next2)) {
                  b = ve[i];
               } 
            }
				int w = s.nextInt();
				e.add(new Edge(a,b,w));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(v,e);
	}
}
