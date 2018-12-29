import React, {Component} from 'react'
import Fileupload from "./file-upload/Fileupload";
import DrawingConfig from "./drawing-config/DrawingConfig";
import {Container} from "semantic-ui-react";

class InputData extends Component {
    constructor(props) {
        super(props);
        this.state = this.getInitialState();
    }

    getInitialState = () => {
        const initialState = {
            drawingId: '',
            fileName: '',
            uploadDone: false,
        };
        return initialState;
    };

    handleFileNameChange = fileName => {
        this.setState({fileName: fileName});
        this.props.onFileNameChange(fileName);
    };

    handleDrawingIdChange = drawingId => {
        this.setState({drawingId: drawingId})
    };

    handleConvertResponse = convertResponse => {
        this.props.onConvertResponse(convertResponse);
    };

    handleUploadFinished = isDone => {
        this.setState({uploadDone: isDone});
        if (isDone) this.props.onProgress(2);
    };

    resetState() {
        this.setState(this.getInitialState);
        if (this.fileUpload) this.fileUpload.resetState();
    }

    toggleLoader = () => {
        this.props.onToggleLoader();
    };

    handleError = message => {
        this.props.onError(message);
    };


    render() {
        let fileName = this.state.fileName;
        let drawingId = this.state.drawingId;
        let isUploadComplete = this.state.uploadDone;

        return (
            <Container fluid>
                {!isUploadComplete ? (
                    <Fileupload ref={fileUpload => this.fileUpload = fileUpload}
                                onUploadFinished={this.handleUploadFinished}
                                onToggleLoader={this.toggleLoader}
                                onFileNameChange={this.handleFileNameChange}
                                onDrawingIdChange={this.handleDrawingIdChange}
                                onError={this.handleError}/>

                ) : (
                    <DrawingConfig fileName={fileName} drawingId={drawingId}
                                   onConvertResponse={this.handleConvertResponse}
                                   onToggleLoader={this.toggleLoader}
                                   onError={this.handleError}/>
                )}
            </Container>
        );

    }
}

export default InputData;