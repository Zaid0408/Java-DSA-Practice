
/*
 * LeetCode 208: Implement Trie (Prefix Tree) – Easy Explanation
Problem:
You're asked to implement a Trie, which is a data structure used for efficient information retrieval, typically strings. The Trie supports three operations:

Insert: Add a word to the Trie.
Search: Check if a word exists in the Trie.
StartsWith: Check if there is any word in the Trie that starts with a given prefix.

Key Concepts:
A Trie is a tree-like data structure where each node represents a character.
Words and prefixes are stored in a way that each character leads to the next level in the tree.
 */

 // with array
 class TrieNode{
    TrieNode children[]=new TrieNode[26];
    boolean endOfWord=false;
}

class Trie {
    TrieNode root;
    public Trie() {
        root=new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode cur=root;
        for(char c: word.toCharArray())
        {
            int i= c-'a';
            if(cur.children[i]==null)
                cur.children[i] = new TrieNode();
            cur=cur.children[i];
        }
        cur.endOfWord=true;
    }
    
    public boolean search(String word) {
        TrieNode cur=root;
        for(char c: word.toCharArray())
        {
            int i= c-'a';
            if(cur.children[i]==null)
                return false;
            cur=cur.children[i];
        }
        return cur.endOfWord;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode cur=root;
        for(char c: prefix.toCharArray())
        {
            int i= c-'a';
            if(cur.children[i]==null)
                return false;
            cur=cur.children[i];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
/*
 * Steps to Implement:
Node Structure: Each node stores:

A dictionary to its children nodes.
A boolean to mark if it's the end of a word.

Insert Operation:

Start from the root and for each character in the word, move to the corresponding child node. If a node doesn’t exist, create it.
Mark the last node as the end of a word.

Search Operation:

Start from the root and follow the path for each character in the word.
If you reach the end of the word and the last node is marked as the end, return true.

StartsWith Operation:

Similar to search, but you don't need to check if it's the end of a word. Just ensure the prefix path exists.
 */