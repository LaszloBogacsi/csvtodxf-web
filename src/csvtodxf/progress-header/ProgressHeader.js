import React, {Component} from 'react';
import {Icon, Step} from "semantic-ui-react";

class ProgressHeader extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ...props
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps !== this.state) {
            console.log('here');
            this.setState(nextProps);
        }
    }

    render() {
        let isUploadComplete = this.state.isUploadComplete;
        let fileName = this.state.fileName;
        return (
            <Step.Group fluid >
                <Step disabled={this.state.stepOne.disabled} completed={this.state.stepOne.isDone} active={this.state.stepOne.active}>
                <Icon name="upload"/>
                    <Step.Content>
                        <Step.Title>{isUploadComplete ? 'Upload finished' : 'Upload'}</Step.Title>
                        <Step.Description>{isUploadComplete ? fileName : 'Upload your csv file '}</Step.Description>
                    </Step.Content>
                </Step>

                <Step disabled={this.state.stepTwo.disabled} completed={this.state.stepTwo.isDone} active={this.state.stepTwo.active}>
                    <Icon name="options"/>
                    <Step.Content>
                        <Step.Title>Options</Step.Title>
                        <Step.Description>Choose drawing options</Step.Description>
                    </Step.Content>
                </Step>

                <Step disabled={this.state.stepThree.disabled} completed={this.state.stepThree.isDone} active={this.state.stepThree.active}>
                    <Icon name="download"/>
                    <Step.Content>
                        <Step.Title>Download</Step.Title>
                        <Step.Description>Download your drawing</Step.Description>
                    </Step.Content>
                </Step>

            </Step.Group>
        )
    }
}

export default ProgressHeader;