package com.example.poppylove;

import java.util.List;

public interface MatchCallback {
    void onMatchSortComplete(List<ProfileData> result);
    void onLinkedComplete(List<String> result);
}
