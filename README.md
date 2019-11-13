# IMDBGraph
Ted Clifford and Marie Tessier

<h1>Introduction</h1>
In this project you will develop software instructure to model large <b>graphs</b> along with a search
engine to find <b>shortest paths</b> between any pair of nodes in a graph. As an example application illustrating
how to use this infrastructure, you will develop a search engine for the Internet Movie Database (IMDB) that allows
users to find a shortest path of actor nodes and movie nodes that connect any two specified actors (if such a path exists).
The code that your team will write to load data and find paths will interface with code (written by me) 
that implements the graphical user interface (GUI -- see figure below) of the application.<p>

<img width="700" src="https://web.cs.wpi.edu/~cs2103/b19/Project3/GraphSearchGUI.png"/>

<h1>Parsing the IMDB data</h1>
One of the tedious but crucial aspects of real-world computer programming is converting data from one format to another.
It's important to become efficient and very reliable at doing this, and this project provides a practice opportunity.
In particular, you will create a class <tt>IMDBGraphImpl</tt> that implements the <tt>IMDBGraph</tt> interface.
Implementing this class will require you to parse data files that contain information
on actresses/actors and the movies in which they starred.<p>

While you are welcome to use more sophisticated parsing methods such as regular expressions, <b>these
are not required for this assignment</b>. In fact, my own implementation simply uses a <tt>Scanner</tt>
(just its <tt>hasNextLine</tt> and <tt>nextLine</tt> methods -- nothing fancier) as well as some standard <tt>String</tt> methods: <tt>indexOf</tt>,
<tt>substring</tt>, and <tt>lastIndexOf</tt>.


<h2>Data format</h2>
The IMDB dataset consist of two data files: <tt>actors.list</tt> and <tt>actresses.list</tt>. Each file starts
with a preamble that describes the file structure. The <tt>actors.list</tt> data file begins in earnest with the lines:
<pre>
THE ACTORS LIST
===============

Name                    Titles 
----                    ------
</pre>
(The <tt>actresses.list</tt> begins analogously.)
In each file, actresses/actors are listed alphabetically. For each actress/actor, the list of movies in which
she/he starred is listed, followed by an empty line to separate the current actress/actor from the next
actress/actor, e.g.:
<pre>
$hutter                 Battle of the Sexes (2017)  (as $hutter Boy)  [Bobby Riggs Fan]  <10>
                        NVTION: The Star Nation Rapumentary (2016)  (as $hutter Boy)  [Himself]  <1>
                        Secret in Their Eyes (2015)  (uncredited)  [2002 Dodger Fan]
                        Steve Jobs (2015)  (uncredited)  [1988 Opera House Patron]
                        Straight Outta Compton (2015)  (uncredited)  [Club Patron/Dopeman]
</pre>
In the example above, an actor named <tt>$hutter</tt> (for some reason he spells his
name with a $ sign) has starred in 5 movies and/or television shows, one movie/show shown on each line.
At least one tab character (<tt>\t</tt>) separates the actor from the first movie/show, and at least one tab
character precedes each movie/show given on subsequent lines.
Note that the actress'/actor's name is <b>not</b> repeated for each movie/show in which she/he starred -- it
is shown only once for the <b>first</b> movie/show.
Along with the name of the movie/show
and the year, additional information may be given including the name of the character and the billing position of the
actor in the credits. <b>In this programming project, you should only parse the title and year of each movie.</b>
In the example above, your parsing code should thus produce the following five strings for the actor <tt>$hutter</tt>:
<ul>
<li><tt>Battle of the Sexes (2017)</tt></li>
<li><tt>NVTION: The Star Nation Rapumentary (2016)</tt></li>
<li><tt>Secret in Their Eyes (2015)</tt></li>
<li><tt>Steve Jobs (2015)</tt></li>
<li><tt>Straight Outta Compton (2015)</tt></li>
</ul>
In particular, <b>the string representing the title of each movie that your code parses should include
the year in parentheses as part of the title string</b>.

<h2>Parsing the data</h2>
The <tt>actors.list</tt> and <tt>actresses.list</tt> data files are <b>not</b> in ASCII format; rather, they are
in <b>ISO-8859-1</b> format. This file format allows the use of non-ASCII characters that are common
in many languages (but not English).
It is essential that you use the proper character decoder when parsing the files. 
We recommend that you use the <tt>java.util.Scanner</tt> class, e.g.:
<pre>
final Scanner scanner = new Scanner(new File(actorsFilename), "ISO-8859-1");
</pre>
The line of code above instantiates a new <tt>Scanner</tt> object that reads from a <tt>java.io.File</tt> (on disk)
and decodes it using a <tt>ISO-8859-1</tt> decoder. From then on, you can use all the standard
methods available through the <tt>Scanner</tt> class; some of the more useful ones include: <tt>hasNextLine</tt>
<tt>nextLine</tt>, and <tt>findInLine</tt>.

<h2>Keeping the memory usage tractable</h2>
The IMDB contains about 30,000,000 lines of data that your code must parse and analyze in order to
build the graph. In order to reduce the space and time costs involved in this project, you should
<b>exclude</b> any <b>TV series</b> as well as any <b>TV movie</b>.
As described in the data files, TV movies contain <tt>(TV)</tt>
in the line describing them, e.g.:<br>
<pre>
                        Sweet Talkin' Guys (1991) (TV)  (archive footage)  [Himself]
</pre>
TV series have quotation marks around the titles, e.g.:
<pre>
                        "All You Need Is Love" (1977) {Introduction (#1.1)}  [Himself]
</pre>
In addition, you should also <b>exclude</b> any actress or actor
who starred <b>only</b> in TV series/TV movies; an example of such an actress/actor is <tt>Aaltio, Mikko</tt>:
<pre>
Aaltio, Mikko           "Bitwisards" (2016) {Bitwisardin saari (#1.10)}  [Jorma]  <6>
                        "Bitwisards" (2016) {Pitkästä ilosta ja tyhjännauramisesta (#1.11)}  [Jorma]  <10>
                        "Bitwisards" (2016) {Uusi aika (#1.8)}  [Jorma]  <10>
</pre>
because he starred only in shows in a TV series.
<p>

<b>Note</b>: do <b>not</b> worry about trying to correct any typos that may be present in IMDB, or trying
to "merge" two actors whose names differ in trivial ways (e.g., the presence/absence of a middle initial).
You should assume that each actress/actor and each movie title in the <tt>actors.list</tt> and
<tt>actresses.list</tt> datasets are fully correct (even though this is not actually the case).

<h2>Implementing the <tt>IMDBGraph</tt> interface</h2>
As mentioned above, one of your tasks in this project is to implement the <tt>IMDBGraph</tt>
interface. This class should contain a public constructor that takes two parameters (in order):
<ol>
<li>The absolute path (e.g., <tt>"C:\MyDirectory\actors.list"</tt>) of the <tt>actors.list</tt> file.</li>
<li>The absolute path (e.g., <tt>"C:\MyDirectory\actresses.list"</tt>) of the <tt>actresses.list</tt> file.</li>
</ol>
Your code <b>must</b> load the IMDB data from the files whose absolute paths are specified in the
parameters passed to the constructors. In addition, these constructors <b>must</b> throw
a <b>java.io.IOException</b> -- they should <b>not</b> attempt to catch this exception themselves.
Both of these criteria are essential to enable us to grade your code automatically.<p>

Specifically, your code should contain the following constructor:
<pre>
public IMDBGraphImpl (String actorsFilename, String actressesFilename) throws IOException {
  // Load data from the specified actorsFilename and actressesFilename ...
}
</pre>
Correspondingly, our test code will invoke your constructors as:
<pre>
graph = new IMDBGraphImpl("IMDB/actors.list", "IMDB/actresses.list");
</pre>

<h1>Finding shortest paths between nodes in a graph</h1>
To find a shortest path between node <tt>s</tt> and <tt>t</tt> in a graph, you should implement a
<b>breadth-first search (BFS)</b>, as described during class. Once you have found a shortest path -- if one exists --
you then need to <b>backtrack</b> from <tt>t</tt> back to <tt>s</tt> and record the sequence of nodes that
were traversed. The result should then be returned back to the caller (the <tt>GraphSearchGUI</tt>, in this case).
Even for large social network graphs consisting of millions of nodes, BFS will be very fast, as it operates in
<em>O</em>(<em>n</em>) time (average case), where <em>n</em>
is the number of nodes in the graph. For this part of the assignment, you should
create a class called <tt>GraphSearchEngineImpl</tt> that implements the <tt>GraphSearchEngine</tt> interface.
The <tt>findShortestPath</tt> method of your <tt>GraphSearchEngineImpl</tt> should return an instance of type
<tt>List&lt;Node&gt;</tt> in which the <b>first</b> element of the list is <tt>s</tt>, the <b>last</b> element of the list
is <tt>t</tt>, and the <b>intermediate</b> nodes constitute a shortest path (alternating between movies and actresses/actors)
that connect nodes <tt>s</tt> and <tt>t</tt>. If no shortest path exists between the pair of nodes, then your method
should return <tt>null</tt>.<p>
<b>Make sure that your <tt>GraphSearchEngineImpl</tt> is not "tied" to the IMDB data in any way</b> -- the search
engine should be useful for <b>any</b> graph of <tt>Node</tt> objects.

<h1>Requirements</h1>
<ol>
<li><b>R1</b><!-- (25 points -- 18 for correctness and 7 for design &amp; style)</b>-->: Create a class called <tt>IMDBGraphImpl</tt>that implements the <tt>Graph</tt> interface:
  <ul>
    <li>The class should load data from the two files whose filenames are specified in the constructor. Your parsing
    code should exclude TV series, TV movies, and any actresses/actors who starred only in TV series/movies.</li>
    <li>The class should have a public constructor, which can throw an <tt>IOException</tt>,
        that take two parameters specifying the absolute paths
        of the actors and actresses data files, in that order.</li>
    <li>The class should construct a graph based on the data parsed from these files.</li>
    <li>To make sure that everyone is parsing the same data files, you <b>must</b> use the IMDB data contained in the Zip
file at the following link: <a href="https://s3.amazonaws.com/jrwprojects/IMDB.zip">IMDB.zip</a>.</li>
  </ul>
</li>
<li><b>R2</b><!-- (25 points -- 20 for correctness and 5 for design &amp; style)</b>-->: Implement BFS within a class <tt>GraphSearchEngineImpl</tt> that implements <tt>GraphSearchEngine</tt>. Your search
engine should be able to find shortest paths between <b>any</b> pair of <tt>Node</tt> objects (or return <tt>null</tt>
if no such path exists) -- whether they are IMDB
nodes, CiteSeer (scientific publication) nodes, or anything else.</li>
</ol>
<b>Make sure that the files you submit are named exactly as described above</b> so that our automatic test
code can give you credit for your work. Note that you may 
<b>not</b> change any of the interfaces that we provide in any way.

<h1>Teamwork</h1>
You may work as a team on this project; the maximum team size is 2.

<h1>Design and Style</h1>
Your code must adhere to reasonable Java style. In particular, please adhere to the following guidelines:
<ul>
<li>Each class name should be a singular noun that can be easily pluralized.</li>
<li>Class names should be in <tt>CamelCase</tt>; variables should be in <tt>mixedCase</tt>.</li>
<li>Avoid "magic numbers" in your code (e.g., <tt>for (int i = 0; i < 999 /*magic number*/; i++)</tt>). Instead,
use <b>constants</b>, e.g., <tt>private static final int NUM_ELEPHANTS_IN_THE_ROOM = 999;</tt>, defined at the top of your class file.</li>

<li>Use whitespace consistently.</li>
<li>No method should exceed 50 lines of code (for a "reasonable" maximum line length, e.g., 100 characters). If your method is larger than
that, it's probably a sign it should be decomposed into a few helper methods.</li>
<li>Use comments to explain non-trivial aspects of code.</li>
<li>Use a <a href="http://www.oracle.com/technetwork/articles/java/index-137868.html">Javadoc</a>
comment to explain what each method does, what parameters it takes, and what it returns. Use
the <tt>/**...*/</tt> syntax along with <tt>@param</tt> and <tt>@return</tt> tags, as appropriate.</li>
<li>Use the <tt>final</tt> keyword whenever possible.</li>
<li>Use the <b>most restrictive</b> access modifiers (e.g., <tt>private</tt>, default, <tt>protected</tt>>, <tt>public</tt>),
for both variables and methods, that you can. Note that this does not mean you can never use non-<tt>private</tt> access; it
just means you should have a good reason for doing so.</li>
<li>Declare variables using the <b>weakest type</b> (e.g., an interface rather than a specific class implementation) you can;
ithen instantiate new objects according to the actual class you need. This will help to ensure <b>maximum flexibility</b> of your code.
For example, instead of<br>
<tt>final ArrayList&lt;String&gt; list = new ArrayList<String>();</tt><br>use<br>
<tt>final List&lt;String&gt; list = new ArrayList&lt;String&gt;();</tt><br>If, on the other hand, you have a good reason
for using the actual type of the object you instantiate (e.g., you need to access specific methods of
<tt>ArrayList</tt> that are not part  of the <tt>List</tt> interface), then it's fine to declare the variable with a stronger type.</li>
</ul>


<h1>Getting started</h1>
Download the <a href="https://www.cs.wpi.edu/~cs2103/b19/Project3/Project3.zip">Project3 starter file</a>.<br>
Download the <a href="https://s3.amazonaws.com/jrwprojects/IMDB.zip">IMDB data</a>.<p>

<b>Note</b>: the <tt>GraphSearchGUI</tt> class is the motivating application of this project, but you are not
required to use it or extend it in any way. It's mainly just for fun.

<h1>Testing</h1>
In this project, we will not grade your test code. However,
<b>we highly recommend that you create a toy dataset</b> to help you develop and debug your BFS code.
Creating toy datasets is a very useful technique in a variety of software engineering (and computer science) domains.
This will greatly reduce the amount of computation that is required to make progress on the project.
It will also make it easier to understand and eliminate any bugs that your code might contain.

<h1>What to Submit</h1>
Create a Zip file containing <tt>IMDBGraphImpl.java</tt> and <tt>GraphSearchEngineImpl.java</tt>,
along with any other files that you wish.
Submit the Zip file you created to Canvas.
Your code will be graded by automatic scripts. <b>Submission deadline</b>: Friday, November 15, at 11:59pm EDT.
