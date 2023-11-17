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

void direct_Left_Traversal(Node* node) {
    if (node) {
        fout << node->key << "\n";
        direct_Left_Traversal(node->left);
        direct_Left_Traversal(node->right);
    }
}

Node* deleteNode(Node* root, int key) {
    if (root == nullptr) {
        return root;
    }

    if (key < root->key) {
        root->left = deleteNode(root->left, key);
    }
    else if (key > root->key) {
        root->right = deleteNode(root->right, key);
    }
    else {
        if (root->left == nullptr && root->right == nullptr) {
            delete root;
            root = nullptr;
        }
        else if (root->left == nullptr) {
            Node* temp = root;
            root = root->right;
            delete temp;
        }
        else if (root->right == nullptr) {
            Node* temp = root;
            root = root->left;
            delete temp;
        }
        else {
            Node* minValueNode = root->right;
            while (minValueNode->left != nullptr) {
                minValueNode = minValueNode->left;
            }
            root->key = minValueNode->key;
            root->right = deleteNode(root->right, minValueNode->key);
        }
    }
    return root;
}

int main() {
    int deleteKey;
    fin >> deleteKey;
    
    Node* root = nullptr;
    int key;

    while (fin >> key) {
        root = insertNode(root, key);
    }

    root = deleteNode(root, deleteKey);
    direct_Left_Traversal(root);
    fin.close();
    fout.close();
    return 0;
}
