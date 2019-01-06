import React, {Component} from 'react';
import Message from "semantic-ui-react/dist/commonjs/collections/Message";

export default class MessageDisplayer extends Component {
    render() {
        const message = this.props.message;

        return (
            <div>
                {Object.keys(message).length > 0 &&
                <Message error
                         header={message.header}
                         content={message.content}/>
                }
            </div>
        )
    }
}