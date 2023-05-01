package Controller.EntailmentControl;

import Model.Data;
import Model.IKnowledgeBase;

public interface IEntailmentCheck {
    void removeEntailments(IKnowledgeBase data, IKnowledgeBase original);
    Data[] whatWasRemoved();
}
