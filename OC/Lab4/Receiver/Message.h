#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include <cstdio>
#pragma warning(disable: 4996)

using namespace std;

struct message {
	char text[21];

	message() {}

	message(string s) {
		if (s.length() > 20) {
			cerr << "Message creating error";
			return;
		}
		strcpy(text, s.c_str());
		text[21] = '\n';
	}

	char* get_text() {
		return text;
	}

	friend ostream& operator <<(ostream& out, const message& m) {
		out.write((char*)&m, sizeof(m));
		return out;

	}

	friend istream& operator >>(istream& in, message& m) {
		in.read((char*)&m, sizeof(m));
		return in;
	}
};