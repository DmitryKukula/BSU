#include <fstream>

using namespace std;

ifstream fin("input.txt");
ofstream fout("output.txt");

struct Node {
    int key;
    Node* left;
    Node* right;
};

Node* createNode(int key) {
    Node* newNode = new Node;
    if (newNode) {
        newNode->key = key;
        newNode->left = nullptr;
        newNode->right = nullptr;
    }
    return newNode;
}

Node* insertNode(Node* node, int key) {
    if (node == nullptr) {
        return createNode(key);
    }

    if (key < node->key) {
        node->left = insertNode(node->left, key);
    }
    else if (key > node->key) {
        node->right = insertNode(node->right, key);
    }

    return node;
}

void preorderTraversal(Node* node) {
    if (node) {
        fout << node->key << "\n";
        preorderTraversal(node->left);
        preorderTraversal(node->right);
    }
}

int main() {
    Node* root = nullptr;
    int key;

    while (fin >> key) {
        root = insertNode(root, key);
    }

    preorderTraversal(root);
    fin.close();
    fout.close();
    return 0;
}
